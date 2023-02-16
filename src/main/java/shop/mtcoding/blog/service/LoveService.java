package shop.mtcoding.blog.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.handler.exception.CustomApiException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.LoveRepository;
import shop.mtcoding.blog.model.User;

@Transactional(readOnly = true)
@Service
public class LoveService {

    @Autowired
    private LoveRepository loveRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpSession session;

    @Transactional
    public void 좋아요입력(int principalId, int boardId) {
        Board boardPS = boardRepository.findById(boardId);
        if (boardPS == null) {
            throw new CustomApiException("존재하지 않는 게시글입니다");
        }

        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("로그인을 해주세요");
        }

        int result = loveRepository.insert(principal.getId(), boardId);
        if (result != 1) {
            throw new CustomApiException("좋아요 입력이 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        boardRepository.insertLikesCountById(boardId);
    }

    @Transactional
    public void 좋아요삭제(int userId, int boardId) {
        Board boardPS = boardRepository.findById(boardId);
        if (boardPS == null) {
            throw new CustomApiException("존재하지 않는 게시글입니다");
        }

        // 제어권이 없으므로 try, catch
        try {
            loveRepository.deleteByUserIdWithBoardId(userId, boardId);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 발생했습니다",
                    HttpStatus.INTERNAL_SERVER_ERROR);
            // 로그를 남겨야 함 (DB or File)
        }
        boardRepository.deleteLikesCountById(boardId);
    }

}
