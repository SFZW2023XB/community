package life.fuzhong.community.model;

import lombok.Data;

@Data
public class Users {
    private Long id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;


}
