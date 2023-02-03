package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardRepository {
    public int insert(@Param("userId") int userId, @Param("title") String title,
            @Param("content") String content);

    public User findById(int id);

    public User findByUserId(int userId);

    public List<User> findAll();
}
