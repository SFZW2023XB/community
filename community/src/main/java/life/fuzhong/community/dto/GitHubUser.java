package life.fuzhong.community.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private String name;
    private long id;
    private String bio;
    private String avatar_url;

}
