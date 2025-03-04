package life.fuzhong.community.dto;

import life.fuzhong.community.model.Users;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long gmtCreate;
    private Long gmtModified;
    private Long commentator;
    private Long likeCount;
    private String content;
    private Users users;
}
