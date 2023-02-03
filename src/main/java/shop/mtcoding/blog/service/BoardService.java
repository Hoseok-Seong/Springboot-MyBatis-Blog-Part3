package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.model.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public int 글쓰기(int userId, String title, String content) {
        // 1. db에 insert하기
        int result = boardRepository.insert(userId, title, content);

        if (result != 1) {
            return -1;
        }

        return 1;
        // commit
    }

}
