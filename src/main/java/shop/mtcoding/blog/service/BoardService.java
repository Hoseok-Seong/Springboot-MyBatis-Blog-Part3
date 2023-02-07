package shop.mtcoding.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.board.BoardReqDto.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardRespDto.BoardMainRespDto;
import shop.mtcoding.blog.handler.exception.CustomException;
import shop.mtcoding.blog.model.BoardRepository;

@Transactional(readOnly = true)
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // where 절에 걸리는 파라미터를 앞에 받기.
    @Transactional
    public void 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId) {
        int result = boardRepository.insert(userId, boardSaveReqDto.getTitle(), boardSaveReqDto.getContent());
        if (result != 1) {
            throw new CustomException("글작성이 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}