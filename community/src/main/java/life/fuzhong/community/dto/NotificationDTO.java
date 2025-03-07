package life.fuzhong.community.dto;

import life.fuzhong.community.model.Users;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Users notifier;
    private String outerTitle;
    private String type;

}
