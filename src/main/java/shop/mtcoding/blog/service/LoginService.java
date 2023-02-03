package shop.mtcoding.blog.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;

@Service
public class LoginService {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public int 로그인하기(String username, String password) {
        // 2. session에 저장
        User user = userRepository.findByUsernameAndPassword(username, password);
        session.setAttribute("principal", user);

        if (session.getAttribute("principal") == null) {
            return -1;
        }

        return 1;
        // commit
    }

}
