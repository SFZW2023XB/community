package life.fuzhong.community.controller;

import jakarta.annotation.Resource;
import life.fuzhong.community.dto.QuestionDTO;
import life.fuzhong.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){

        QuestionDTO questionDTO = questionService.getByID(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
