package life.fuzhong.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {
    @GetMapping("/")
    public String index (){

        return "index";
    }




}
