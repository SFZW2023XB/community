package life.fuzhong.community.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import life.fuzhong.community.model.Users;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;

}
