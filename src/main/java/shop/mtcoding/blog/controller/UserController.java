package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.blog.handler.exception.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "/user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/user/{id}/updateForm")
    public String updateForm(@PathVariable int id) {
        return "/user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {
        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("아이디를 작성해주세요.");
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("비밀번호를 작성해주세요.");
        }
        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("이메일을 작성해주세요.");
        }

        int result = userService.가입하기(joinReqDto);

        if (result != 1) {
            throw new CustomException("회원가입이 실패하였습니다.");
        }

        return "redirect:/loginForm";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        // 1. 유저 유효성 검사
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user == null) {
            throw new CustomException("존재하지 않는 아이디거나 비밀번호를 다시 확인해주시기 바랍니다.");
        }

        int result = userService.로그인하기(username, password);

        if (result == -1) {
            throw new CustomException("로그인이 실패하였습니다.");
        }

        return "redirect:/";
    }

}
