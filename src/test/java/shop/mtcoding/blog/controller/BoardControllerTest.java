package shop.mtcoding.blog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog.dto.board.BoardReqDto.BoardUpdateReqDto;
import shop.mtcoding.blog.dto.board.BoardRespDto;
import shop.mtcoding.blog.dto.board.BoardRespDto.BoardDetailRespDto;
import shop.mtcoding.blog.model.User;

/*
 * SpringBootTest는 통합테스트 (실제 환경과 동일하게 Bean이 생성됨)
 * AutoConfigureMockMvc는 Mock 환경의 IoC컨테이너에 MockMvc Bean이 생성됨
 */
@Transactional // 메서드 실행 직후 롤백! // auto_increment 초기화 안 됨.
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    private MockHttpSession mockSession; // autowired 불가.

    @BeforeEach // Test 메서드 실행 직전에 호출됨
    public void setUp() { // 세션 가짜로 만들기
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void save_test() throws Exception {
        // given
        String title = "";
        for (int i = 0; i < 99; i++) { // 글자수로 인식
            title += "가";
        }

        String requestBody = "title=" + title + "&content=내용1";

        // when
        ResultActions resultActions = mvc.perform(post("/board").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).session(mockSession));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void main_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/"));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        List<BoardRespDto.BoardMainRespDto> dtos = (List<BoardRespDto.BoardMainRespDto>) map.get("dtos");
        String model = om.writeValueAsString(dtos); // json 변환.
        System.out.println("테스트 : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dtos.size()).isEqualTo(12);
        assertThat(dtos.get(0).getUsername()).isEqualTo("ssar");
        assertThat(dtos.get(5).getTitle()).isEqualTo("제목6");
    }

    @Test
    public void detail_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(get("/board/" + id));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        BoardDetailRespDto dto = (BoardDetailRespDto) map.get("dto");
        String model = om.writeValueAsString(dto); // json 변환.
        System.out.println("테스트 : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dto.getUsername()).isEqualTo("ssar");
        assertThat(dto.getUserId()).isEqualTo(1);
        assertThat(dto.getTitle()).isEqualTo("제목1");
    }

    @Test
    public void delete_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/board/" + id).session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        /*
         * jsonPath
         * 최상위 : $
         * 객체탐색: 닷(.)
         * 배열: [0]
         */
        // then
        resultActions.andExpect(jsonPath("$.code").value(1)); // json 데이터를 받음
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void update_test() throws Exception {
        // given
        int id = 1;
        BoardUpdateReqDto boardUpdateReqDto = new BoardUpdateReqDto();
        boardUpdateReqDto.setTitle("제목1-수정");
        boardUpdateReqDto.setContent("내용1-수정");

        String requestBody = om.writeValueAsString(boardUpdateReqDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(put("/board/" + id +
                "/update").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE).session(mockSession));

        // /*
        // * jsonPath
        // * 최상위 : $
        // * 객체탐색: 닷(.)
        // * 배열: [0]
        // */

        // then
        resultActions.andExpect(jsonPath("$.code").value(1)); // json 데이터를 받음
        resultActions.andExpect(status().isOk());
    }
}