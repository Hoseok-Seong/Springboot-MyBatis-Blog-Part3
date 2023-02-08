package shop.mtcoding.blog.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardRespDto {

    @Getter
    @Setter
    public static class BoardMainRespDto {
        private int id;
        private String title;
        private String username;
        private String thumbnail;
    }

    @Getter
    @Setter
    public static class BoardDetailRespDto {
        private int id;
        private int userId;
        private String title;
        private String username;
        private String content;
    }

    @Getter
    @Setter
    public static class BoardUpdateRespDto {
        private int id;
        private int userId;
        private String title;
        private String username;
        private String content;
        private String thumbnail;
    }

}
