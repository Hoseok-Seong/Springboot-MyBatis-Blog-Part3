package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoveRepository {
    public List<Love> findAll();

    public Love findById(int id);

    public int insert(@Param("userId") int userId, @Param("boardId") int boardId);

    public int updateById(@Param("userId") int userId, @Param("boardId") int boardId);

    public int deleteById(int id);

    public int deleteByUserIdWithBoardId(@Param("userId") int userId, @Param("boardId") int boardId);

}
