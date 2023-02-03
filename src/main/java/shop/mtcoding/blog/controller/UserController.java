package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.JoinService;
import shop.mtcoding.blog.service.LoginService;

@Controller
public class UserController {
    @Autowired
    private JoinService joinService;

    @Autowired
    private LoginService loginService;

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
    public String join(String username, String password, String email) {
        int result = joinService.가입하기(username, password, email);

        if (result == -1) {
            return "redirect:/joinForm";
        }

        return "redirect:/loginForm";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        // 1. 유저 유효성 검사
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user == null) {
            return "redirect:/loginForm";
        }

        int result = loginService.로그인하기(username, password);

        if (result == -1) {
            return "redirect:/loginForm";
        }

        return "redirect:/";
    }

}
