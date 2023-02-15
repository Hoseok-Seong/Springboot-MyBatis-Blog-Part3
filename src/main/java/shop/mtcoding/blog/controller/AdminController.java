package shop.mtcoding.blog.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.user.UserReqDto.LoginReqDto;
import shop.mtcoding.blog.handler.exception.CustomApiException;
import shop.mtcoding.blog.handler.exception.CustomException;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.ReplyRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.BoardService;
import shop.mtcoding.blog.service.ReplyService;
import shop.mtcoding.blog.service.UserService;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/admin/loginForm")
    public String adminLoginForm() {
        return "admin/loginForm";
    }

    @PostMapping("/admin/login")
    public String adminLogin(LoginReqDto loginReqDto) {
        session.invalidate();

        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("아이디를 작성해주세요");
        }

        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("비밀번호를 작성해주세요");
        }

        // 1. 로그인하기 service
        User principal = userService.로그인하기(loginReqDto);

        // 2. session에 저장
        session.setAttribute("principal", principal);

        // 3. principal 유효성 검사
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("관리자만 접속 가능합니다.");
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user")
    public String adminUser(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/admin/loginForm";
        }

        if (!principal.getRole().equals("admin")) {
            throw new CustomException("관리자만 접속 가능합니다.");
        }

        model.addAttribute("userInfo", userRepository.findAll());
        return "admin/user";
    }

    @DeleteMapping("/admin/user/{id}")
    public @ResponseBody ResponseEntity<?> deleteUser(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        if (!principal.getRole().equals("admin")) {
            throw new CustomApiException("관리자만 접속 가능합니다.");
        }

        userService.회원삭제(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제 성공", null), HttpStatus.OK);

    }

    @GetMapping("/admin/board")
    public String adminBoard(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/admin/loginForm";
        }

        if (!principal.getRole().equals("admin")) {
            throw new CustomException("관리자만 접속 가능합니다.");
        }

        model.addAttribute("boardInfo", boardRepository.findAll());

        return "admin/board";
    }

    @DeleteMapping("/admin/board/{id}")
    public @ResponseBody ResponseEntity<?> deleteBoard(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        if (!principal.getRole().equals("admin")) {
            throw new CustomApiException("관리자만 접속 가능합니다.");
        }

        boardService.게시글삭제(id, principal.getId(), principal.getRole());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제 성공", null), HttpStatus.OK);

    }

    @GetMapping("/admin/reply")
    public String adminReply(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/admin/loginForm";
        }

        if (!principal.getRole().equals("admin")) {
            throw new CustomException("관리자만 접속 가능합니다.");
        }

        model.addAttribute("replyInfo", replyRepository.findAll());

        return "admin/reply";
    }

    @DeleteMapping("/admin/reply/{id}")
    public @ResponseBody ResponseEntity<?> deleteReply(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        if (!principal.getRole().equals("admin")) {
            throw new CustomApiException("관리자만 접속 가능합니다.");
        }

        replyService.댓글삭제(id, principal.getId(), principal.getRole());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제 성공", null), HttpStatus.OK);

    }
}
