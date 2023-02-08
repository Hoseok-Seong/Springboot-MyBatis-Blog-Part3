package shop.mtcoding.blog.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.board.BoardReqDto.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReqDto.BoardUpdateReqDto;
import shop.mtcoding.blog.dto.board.BoardRespDto.BoardUpdateRespDto;
import shop.mtcoding.blog.handler.exception.CustomApiException;
import shop.mtcoding.blog.handler.exception.CustomException;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.service.BoardService;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping({ "/", "/board" })
    public String main(Model model) {
        model.addAttribute("dtos", boardRepository.findAllWithUser());
        return "/board/main";
    }

    @PostMapping("/board")
    public String save(BoardSaveReqDto boardSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }
        if (boardSaveReqDto.getTitle() == null ||
                boardSaveReqDto.getTitle().isEmpty()) {
            throw new CustomException("제목을 작성해주세요");
        }

        if (boardSaveReqDto.getTitle().length() > 100) {
            throw new CustomException("제목의 길이는 100자 이하까지 가능합니다");
        }

        if (boardSaveReqDto.getContent() == null ||
                boardSaveReqDto.getContent().isEmpty()) {
            throw new CustomException("글 내용을 작성해주세요");
        }

        boardService.글쓰기(boardSaveReqDto, principal.getId());

        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("dto", boardRepository.findByIdWithUser(id));
        return "/board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "/board/saveForm";
    }

    @DeleteMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        boardService.게시글삭제(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제 성공", null), HttpStatus.OK);

    }

    // id는 게시글 번호
    @GetMapping("/board/{id}/updateForm")
    public String boardUpdateForm(@PathVariable int id, Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }
        BoardUpdateRespDto dto = boardRepository.findByIdForUpdate(id);
        if (dto == null) {
            throw new CustomException("존재하지 않는 게시글입니다");
        }
        if (principal.getId() != dto.getUserId()) {
            throw new CustomException("해당 게시글을 수정할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        model.addAttribute("dto", dto);
        return "/board/boardUpdateForm";
    }

    @PutMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id,
            @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        if (boardUpdateReqDto.getTitle() == null ||
                boardUpdateReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("제목을 작성해주세요");
        }

        if (boardUpdateReqDto.getTitle().length() > 100) {
            throw new CustomApiException("제목의 길이는 100자 이하까지 가능합니다");
        }

        if (boardUpdateReqDto.getContent() == null ||
                boardUpdateReqDto.getContent().isEmpty()) {
            throw new CustomApiException("글 내용을 작성해주세요");
        }

        boardService.게시글수정(id, boardUpdateReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 수정 성공", null), HttpStatus.OK);
    }

}
