package life.fuzhong.community.controller;


import jakarta.annotation.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.fuzhong.community.cache.TagCache;
import life.fuzhong.community.dto.QuestionDTO;
import life.fuzhong.community.mapper.QuestionMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Question;
import life.fuzhong.community.model.Users;
import life.fuzhong.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublishController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.getByID(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model){

        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title" ) String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

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

        Users users = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    users = userMapper.findBytoken(token);
                    if (users != null) {
                        request.getSession().setAttribute("users", users);
                    }
                    break;
                }
            }
        }



        if(users == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(users.getId());

        question.setId(id);
        questionService.insertOrUpdate(question);


            return "redirect:/";
    }


}
