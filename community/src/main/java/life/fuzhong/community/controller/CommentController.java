package life.fuzhong.community.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import life.fuzhong.community.dto.CommentCreateDTO;
import life.fuzhong.community.dto.CommentDTO;
import life.fuzhong.community.dto.ResultDTO;
import life.fuzhong.community.exception.CustomizeErrorCode;
import life.fuzhong.community.model.Comment;
import life.fuzhong.community.model.Users;
import life.fuzhong.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {
    @Resource
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){

        Users users =(Users) request.getSession().getAttribute("users");
        if (users == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }


        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(users.getId());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        commentService.insert(comment, users);


        return ResultDTO.successOf();
    }


    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable (name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByCommentId(id);
        return ResultDTO.successOf(commentDTOS);
    }


}
