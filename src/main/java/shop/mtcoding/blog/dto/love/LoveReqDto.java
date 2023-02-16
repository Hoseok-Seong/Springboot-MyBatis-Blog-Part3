package shop.mtcoding.blog.dto.love;

import lombok.Getter;
import lombok.Setter;

public class LoveReqDto {

    @Setter
    @Getter
    public static class LoveAllReqDto {
        private Integer userId;
        private Integer boardId;
    }
}
