package shop.mtcoding.blog.dto.reply;

import lombok.Getter;
import lombok.Setter;

public class ReplyReqDto {

    @Setter
    @Getter
    public static class ReplySaveReqDto {
        private String comment;
        private Integer boardId;
    }
}
