package shop.mtcoding.blog.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    private int id;
    private int userId;
    private String title;
    private String content;
    private String thumbnail;
    private Integer likes;
    private Timestamp createdAt;
}
