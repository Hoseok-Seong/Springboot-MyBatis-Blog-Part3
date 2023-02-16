package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.love.LoveReqDto.LoveAllReqDto;
import shop.mtcoding.blog.handler.exception.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.service.LoveService;

@Controller
public class LoveController {

    @Autowired
    private LoveService loveService;

    @Autowired
    private HttpSession session;

    @PostMapping("/love/insert")
    public @ResponseBody String insert(@RequestBody LoveAllReqDto loveAllReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요한 요청입니다");
        }
        loveService.좋아요입력(principal.getId(), loveAllReqDto.getBoardId());
        return "redirect:/detail";
    }

    @DeleteMapping("/love/delete")
    public @ResponseBody ResponseEntity<?> delete(@RequestBody LoveAllReqDto loveAllReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요한 요청입니다");
        }
        loveService.좋아요삭제(principal.getId(), loveAllReqDto.getBoardId());
        // return "redirect:/detail";

        return new ResponseEntity<>(new ResponseDto<>(1, "삭제 성공", null), HttpStatus.OK);
    }
}
