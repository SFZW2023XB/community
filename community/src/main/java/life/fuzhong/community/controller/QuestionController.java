package life.fuzhong.community.controller;

import jakarta.annotation.Resource;
import life.fuzhong.community.dto.CommentCreateDTO;
import life.fuzhong.community.dto.CommentDTO;
import life.fuzhong.community.dto.QuestionDTO;
import life.fuzhong.community.service.CommentService;
import life.fuzhong.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;
    @Resource
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){



        QuestionDTO questionDTO = questionService.getByID(id);

        List<CommentDTO> comments = commentService.listByQuestionId(id);
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        return "question";
    }
}
