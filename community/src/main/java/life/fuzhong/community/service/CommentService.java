package life.fuzhong.community.service;

import jakarta.annotation.Resource;
import life.fuzhong.community.enums.CommentTypeEnum;
import life.fuzhong.community.exception.CustomizeErrorCode;
import life.fuzhong.community.exception.CustomizeException;
import life.fuzhong.community.mapper.CommentMapper;
import life.fuzhong.community.mapper.QuestionMapper;
import life.fuzhong.community.model.Comment;
import life.fuzhong.community.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.create(comment);


        }else {
            //回复问题
            Question question = questionMapper.findByID(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.create(comment);
            questionMapper.incCommentCount(question);

        }
    }
}
