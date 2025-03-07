package life.fuzhong.community.service;

import jakarta.annotation.Resource;
import life.fuzhong.community.dto.CommentDTO;
import life.fuzhong.community.enums.CommentTypeEnum;
import life.fuzhong.community.enums.NotificationEnum;
import life.fuzhong.community.enums.NotificationStatusEnum;
import life.fuzhong.community.exception.CustomizeErrorCode;
import life.fuzhong.community.exception.CustomizeException;
import life.fuzhong.community.mapper.CommentMapper;
import life.fuzhong.community.mapper.NotificationMapper;
import life.fuzhong.community.mapper.QuestionMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Comment;
import life.fuzhong.community.model.Notification;
import life.fuzhong.community.model.Question;
import life.fuzhong.community.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private NotificationMapper notificationMapper;

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
            createNotify(comment, dbComment.getCommentator(), NotificationEnum.REPLY_COMMMENT);


            commentMapper.incCommentCount(dbComment);


        }else {
            //回复问题
            Question question = questionMapper.findByID(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.create(comment);
            createNotify(comment, question.getCreator(), NotificationEnum.REPLY_QUESTOIN);
            questionMapper.incCommentCount(question);

        }
    }

    private void createNotify(Comment comment, Long target, NotificationEnum notificationEnum) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationEnum.getType());
        notification.setOuterId(comment.getParentId());
        notification.setReceiver(target);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.create(notification);
    }

    public List<CommentDTO> listByQuestionId(Long id) {

        List<Comment> comments = commentMapper.selectByQuestionId(id);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //这里没看懂？
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> usersIds = new ArrayList<>();

        usersIds.addAll(commentators);
        List<Users> allUsers = userMapper.selectAllUsers(usersIds);
        Map<Long, Users> usersMap = allUsers.stream().collect(Collectors.toMap(users -> users.getId(), users -> users));
        List<CommentDTO> commentDTOList = comments.stream().map(comment ->{
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUsers(usersMap.get(comment.getCommentator()));
            return commentDTO;
        }).sorted(Comparator.comparing(CommentDTO::getGmtCreate).reversed())
                .collect(Collectors.toList());

        return commentDTOList;
    }

    public List<CommentDTO> listByCommentId(Long id) {

        List<Comment> comments = commentMapper.selectByCommentId(id);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //这里没看懂？
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> usersIds = new ArrayList<>();

        usersIds.addAll(commentators);
        List<Users> allUsers = userMapper.selectAllUsers(usersIds);
        Map<Long, Users> usersMap = allUsers.stream().collect(Collectors.toMap(users -> users.getId(), users -> users));
        List<CommentDTO> commentDTOList = comments.stream().map(comment ->{
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment, commentDTO);
                    commentDTO.setUsers(usersMap.get(comment.getCommentator()));
                    return commentDTO;
                }).sorted(Comparator.comparing(CommentDTO::getGmtCreate).reversed())
                .collect(Collectors.toList());

        return commentDTOList;
    }
}
