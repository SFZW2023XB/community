package life.fuzhong.community.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.fuzhong.community.dto.PaginationDTO;
import life.fuzhong.community.dto.QuestionDTO;
import life.fuzhong.community.mapper.QuestionMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Question;
import life.fuzhong.community.model.Users;
import life.fuzhong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class indexController {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private QuestionService questionService;

    @GetMapping("/")
    public String index (Model model,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size){



        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);

        

        return "index";
    }




}
