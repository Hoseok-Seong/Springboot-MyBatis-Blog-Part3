package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
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

    @GetMapping("/logut")
    public String logout() {
        return "redirect:/";
    }

}
