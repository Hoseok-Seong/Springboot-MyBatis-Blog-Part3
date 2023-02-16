package shop.mtcoding.blog.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Love {
    private int id;
    private int userId;
    private int boardId;
    private Timestamp createdAt;
}
