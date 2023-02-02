package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping("/")
    public String main() {
        return "/board/main";
    }

    @GetMapping("/detail")
    public String detail() {
        return "/board/detail";
    }

    @GetMapping("/saveForm")
    public String saveForm() {
        return "/board/saveForm";
    }

    @GetMapping("/board/updateForm")
    public String boardUpdateForm() {
        return "/board/boardUpdateForm";
    }
}
