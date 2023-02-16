package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.blog.dto.board.BoardRespDto.BoardDetailRespDto;
import shop.mtcoding.blog.dto.board.BoardRespDto.BoardMainRespDto;
import shop.mtcoding.blog.dto.board.BoardRespDto.BoardUpdateRespDto;

@Mapper
public interface BoardRepository {
        public int insert(@Param("userId") int userId, @Param("title") String title,
                        @Param("content") String content, @Param("thumbnail") String thumbnail);

        public int updateById(@Param("id") int id, @Param("title") String title,
                        @Param("content") String content, @Param("thumbnail") String thumbnail);

        public int deleteById(int id);

        public Board findById(int id);

        public Board findByUserId(int userId);

        public List<Board> findAll();

        public List<BoardMainRespDto> findAllWithUser();

        public BoardDetailRespDto findByIdWithUser(int id);

        public BoardUpdateRespDto findByIdForUpdate(int id);

        public int insertLikesCountById(int id);

        public int deleteLikesCountById(int id);

        public List<Board> findByTitle(String title);

}
