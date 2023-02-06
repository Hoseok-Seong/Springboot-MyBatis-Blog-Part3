package shop.mtcoding.blog.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReqDto.LoginReqDto;
import shop.mtcoding.blog.handler.exception.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;

@Service
public class UserService {
    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public int 가입하기(JoinReqDto joinReqDto) {
        // 1. 유저 유효성 검사
        User sameuser = userRepository.findByName(joinReqDto.getUsername());

        if (sameuser != null) {
            throw new CustomException("동일한 아이디가 존재합니다.");
        }
        // 1. db에 insert하기
        int result = userRepository.insert(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());

        if (result != 1) {
            return -1;
        }

        return 1;
        // commit
    }

    @Transactional(readOnly = true)
    public User 로그인하기(LoginReqDto loginReqDto) {
        // 1. db에 select하기
        User principal = userRepository.findByUsernameAndPassword(loginReqDto.getUsername(), loginReqDto.getPassword());

        // 2. principal 유효성 검사
        if (principal == null) {
            throw new CustomException("존재하지 않는 아이디거나 비밀번호를 다시 확인해주시기 바랍니다.");
        }

        return principal;
    }
}
