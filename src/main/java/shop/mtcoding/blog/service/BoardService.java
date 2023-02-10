package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import shop.mtcoding.blog.dto.board.BoardReqDto.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReqDto.BoardUpdateReqDto;
import shop.mtcoding.blog.dto.board.BoardRespDto.BoardUpdateRespDto;
import shop.mtcoding.blog.handler.exception.CustomApiException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.util.ThumbnailParser;

@Transactional(readOnly = true)
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // where 절에 걸리는 파라미터를 앞에 받기.
    @Transactional
    public void 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId) {

        String img = ThumbnailParser.thumbnailParser(boardSaveReqDto.getContent());

        int result = boardRepository.insert(userId, boardSaveReqDto.getTitle(), boardSaveReqDto.getContent(), img);
        if (result != 1) {
            throw new CustomApiException("글 작성이 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public void 게시글삭제(int id, int userId) {
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomApiException("존재하지 않는 게시글입니다");
        }
        if (boardPS.getUserId() != userId) {
            throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다", HttpStatus.FORBIDDEN);

        }

        // 제어권이 없으므로 try, catch
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
            // 로그를 남겨야 함 (DB or File)
        }
    }

    @Transactional
    public void 게시글수정(int id, @RequestBody BoardUpdateReqDto boardUpdateReqDto, int principalId) {
        BoardUpdateRespDto dto = boardRepository.findByIdForUpdate(id);
        if (dto == null) {
            throw new CustomApiException("해당 게시글을 찾을 수 없습니다");
        }
        if (principalId != dto.getUserId()) {
            throw new CustomApiException("해당 게시글을 수정할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        String img = ThumbnailParser.thumbnailParser(boardUpdateReqDto.getContent());

        int result = boardRepository.updateById(id, boardUpdateReqDto.getTitle(),
                boardUpdateReqDto.getContent(), img);

        if (result != 1) {
            throw new CustomApiException("게시글 수정에 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}