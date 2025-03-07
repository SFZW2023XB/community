package life.fuzhong.community.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.fuzhong.community.dto.PaginationDTO;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Users;
import life.fuzhong.community.service.NotificationService;
import life.fuzhong.community.service.QuestionService;
import okhttp3.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    @Resource
    private QuestionService questionService;
    @Resource
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){

        Users users =(Users) request.getSession().getAttribute("users");

        if(users == null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            PaginationDTO paginationDTO = questionService.list(users.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);

        }else if ("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            PaginationDTO paginationDTO = notificationService.list(users.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);

        }

        PaginationDTO paginationDTO = questionService.list(users.getId(), page, size);
        model.addAttribute("pagination", paginationDTO);

        return "profile";
    }
}
