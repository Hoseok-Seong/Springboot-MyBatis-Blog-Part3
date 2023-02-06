package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.blog.dto.board.BoardRespDto.BoardMainRespDto;

@Mapper
public interface BoardRepository {
        public int insert(@Param("userId") int userId, @Param("title") String title,
                        @Param("content") String content);

        public int updateById(@Param("id") int id, @Param("title") String title,
                        @Param("content") String content);

        public int deleteById(int id);

        public Board findById(int id);

        public Board findByUserId(int userId);

        public List<Board> findAll();

        public List<BoardMainRespDto> findAllWithUser();

}
