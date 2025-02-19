package life.fuzhong.community.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index (HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                Users users = userMapper.findBytoken(token);
                if(users != null){
                    request.getSession().setAttribute("user", users);
                }
                break;
            }
        }

        

        return "index";
    }




}
