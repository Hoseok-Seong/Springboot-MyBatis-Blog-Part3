package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.model.UserRepository;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public int 가입하기(String username, String password, String email) {
        // 1. db에 insert하기
        int result = userRepository.insert(username, password, email);

        if (result != 1) {
            return -1;
        }

        return 1;
        // commit
    }

}
