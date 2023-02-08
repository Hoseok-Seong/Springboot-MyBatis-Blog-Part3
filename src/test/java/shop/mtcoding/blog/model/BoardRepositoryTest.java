package shop.mtcoding.blog.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog.dto.board.BoardRespDto.BoardMainRespDto;

// Transactional이 내부 어노테이션 되어 있음.
@MybatisTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findAllWithUser_test() throws Exception {
        // given
        ObjectMapper om = new ObjectMapper();

        // when
        List<BoardMainRespDto> boardMainRespDto = boardRepository.findAllWithUser();
        String responseBody = om.writeValueAsString(boardMainRespDto); // json 변환.
        System.out.println("테스트 : " + responseBody);

        // then
        assertThat(boardMainRespDto.get(5).getUsername()).isEqualTo("love");
    }
}