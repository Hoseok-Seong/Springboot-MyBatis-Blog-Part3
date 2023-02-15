package shop.mtcoding.blog.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.blog.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReqDto.LoginReqDto;
import shop.mtcoding.blog.handler.exception.CustomApiException;
import shop.mtcoding.blog.handler.exception.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.util.PathUtil;

@Service
public class UserService {
    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 가입하기(JoinReqDto joinReqDto) {
        // 1. 유저 유효성 검사
        User sameuser = userRepository.findByName(joinReqDto.getUsername());

        if (sameuser != null) {
            throw new CustomException("동일한 아이디가 존재합니다");
        }
        // 1. db에 insert하기
        int result = userRepository.insert(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());

        if (result != 1) {
            throw new CustomException("회원가입이 실패하였습니다");
        }
    }

    @Transactional(readOnly = true)
    public User 로그인하기(LoginReqDto loginReqDto) {
        // 1. db에 select하기
        User principal = userRepository.findByUsernameAndPassword(loginReqDto.getUsername(), loginReqDto.getPassword());

        // 2. principal 유효성 검사
        if (principal == null) {
            throw new CustomException("존재하지 않는 아이디거나 비밀번호를 다시 확인해주시기 바랍니다");
        }

        return principal;
    }

    @Transactional
    public User 프로필사진수정(MultipartFile profile, int pricipalId) {
        // 1번 사진을 /static/image에 UUID로 변경해서 저장
        String uuidImageName = PathUtil.writeImageFile(profile);

        // 2번 저장된 파일의 경로를 DB에 저장
        // 조회
        User userPS = userRepository.findById(pricipalId);
        userPS.setProfile(uuidImageName);
        userRepository.updateById(userPS.getId(), userPS.getUsername(), userPS.getPassword(), userPS.getEmail(),
                userPS.getProfile(), userPS.getCreatedAt());
        return userPS;
    }

    @Transactional
    public void 회원삭제(int id) {
        int result = userRepository.deleteById(id);
        if (result != 1) {
            throw new CustomApiException("회원삭제가 실패하였습니다");
        }
    }
}
