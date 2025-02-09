package life.fuzhong.community.controller;

import life.fuzhong.community.dto.AccessTokenDTO;
import life.fuzhong.community.dto.GitHubUser;
import life.fuzhong.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id("Ov23liv0K3c4GiHBkWG8") ;
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret("84de1879d5ba570541e9943df457f5945bc2b7bf");
        accessTokenDTO.setRedict_uri("http://localhost:8887/callback");

        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        System.out.println(gitHubUser.getName());

        return "index";
    }


}
