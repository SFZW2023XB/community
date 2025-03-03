package life.fuzhong.community.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import life.fuzhong.community.dto.CommentDTO;
import life.fuzhong.community.dto.ResultDTO;
import life.fuzhong.community.exception.CustomizeErrorCode;
import life.fuzhong.community.exception.CustomizeException;
import life.fuzhong.community.mapper.CommentMapper;
import life.fuzhong.community.model.Comment;
import life.fuzhong.community.model.Users;
import life.fuzhong.community.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class CommentController {
    @Resource
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){

        Users users =(Users) request.getSession().getAttribute("users");
        if (users == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }


        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(users.getId());
        comment.setLikeCount(0);
        commentService.insert(comment);


        return ResultDTO.successOf();
    }
}
