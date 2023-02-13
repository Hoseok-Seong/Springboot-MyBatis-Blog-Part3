package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import shop.mtcoding.blog.dto.reply.ReplyReqDto.ReplySaveReqDto;
import shop.mtcoding.blog.handler.exception.CustomApiException;
import shop.mtcoding.blog.handler.exception.CustomException;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.ReplyRepository;

@Slf4j // 로그를 쓸 수 있다
@Transactional(readOnly = true)
@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    // where 절에 걸리는 파라미터를 앞에 받기.
    @Transactional
    public void 댓글쓰기(ReplySaveReqDto replySaveReqDto, int principalId) {

        int result = replyRepository.insert(replySaveReqDto.getComment(), replySaveReqDto.getBoardId(), principalId);
        if (result != 1) {
            throw new CustomException("댓글 작성이 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public void 댓글삭제(int id, int principalId) {
        Reply reply = replyRepository.findById(id);
        if (reply == null) {
            throw new CustomApiException("댓글이 존재하지 않습니다");
        }
        if (reply.getUserId() != principalId) {
            throw new CustomApiException("댓글을 삭제할 권한이 없습니다", HttpStatus.FORBIDDEN);

        }
        // 1. 인증 ok, 2. 댓글 존재 유무 3. 권한 ok
        try {
            replyRepository.deleteById(id); // 핵심 로직
        } catch (Exception e) {
            log.error("서버에러 : " + e.getMessage());
            // 버퍼를 달고, 파일에 쓰기
            throw new CustomApiException("댓글 삭제 실패 - 서버 에러", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}