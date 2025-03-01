package life.fuzhong.community.controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.fuzhong.community.mapper.QuestionMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Question;
import life.fuzhong.community.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublishController {
    @Resource
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title" ) String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if(title == null || title == ""){
            model.addAttribute("errorTitle", "标题不能为空");
            return "publish";

        }
        if(description == null || description == ""){
            model.addAttribute("errorDescription","问题补充不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("errorTag", "标签不能为空");
            return "publish";
        }

        Users users =(Users) request.getSession().getAttribute("users");

        if(users == null){
            model.addAttribute("error", "用户未登录");
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(users.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);

            return "redirect:/";
    }


}
