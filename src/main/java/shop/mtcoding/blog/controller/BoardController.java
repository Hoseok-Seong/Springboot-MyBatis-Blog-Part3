package shop.mtcoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.service.BoardService;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping({ "/", "/board" })
    public String main() {
        return "/board/main";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id) {
        return "/board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "/board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String boardUpdateForm(@PathVariable int id) {
        return "/board/boardUpdateForm";
    }

    @PostMapping("/board/{userId}/save")
    public String save(@PathVariable int userId, String title, String content) {
        int result = boardService.글쓰기(userId, title, content);

        if (result == -1) {
            return "redirect:/fail";
        }

        return "redirect:/";

    }

}
