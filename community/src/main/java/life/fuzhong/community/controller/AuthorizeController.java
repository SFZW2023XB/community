package life.fuzhong.community.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import life.fuzhong.community.dto.AccessTokenDTO;
import life.fuzhong.community.dto.GitHubUser;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Users;
import life.fuzhong.community.provider.GitHubProvider;
import life.fuzhong.community.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

//    private final UserMapper userMapper;
//    public AuthorizeController(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }


    @Resource
    private UsersService usersService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state", required = false) String state,
                           HttpServletResponse response){
        //System.out.println("Received code: " + code);



        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setGrant_type("authorization_code");

        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if(gitHubUser != null ){
            Users users = new Users();
            String token = UUID.randomUUID().toString();
            users.setToken(token);
            users.setName(gitHubUser.getName());
            users.setAccountId(String.valueOf(gitHubUser.getId()));
            users.setAvatarUrl(gitHubUser.getAvatar_url());

            usersService.insertOrUpdate(users);
            response.addCookie(new Cookie("token", token));


            return "redirect:/";

        }else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response
                         ){
        request.getSession().removeAttribute("users");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
