package life.fuzhong.community.service;

import jakarta.annotation.Resource;
import life.fuzhong.community.dto.CommentDTO;
import life.fuzhong.community.dto.PaginationDTO;
import life.fuzhong.community.dto.QuestionDTO;
import life.fuzhong.community.exception.CustomizeErrorCode;
import life.fuzhong.community.exception.CustomizeException;
import life.fuzhong.community.mapper.QuestionMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Question;
import life.fuzhong.community.model.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();


        Integer totalPage = (totalCount + size - 1) / size;
        if (page <= 0) page = 1;
        if (totalPage == 0) totalPage = 1;
        if (page > totalPage) page = totalPage;

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);

        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questionList) {
            Users users = userMapper.findByID(question.getCreator());
            if (users == null) {
                // 如果没有找到用户，使用默认的用户对象或跳过
                users = new Users();
                users.setAvatarUrl("https://assets.leetcode.cn/aliyun-lc-upload/default_avatar.png");  // 设置默认头像
            }
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUsers(users);
            questionDTOList.add(questionDTO);
        };
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO list(Long usersID, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUsersID(usersID);

        Integer totalPage = (totalCount + size - 1) / size;
        if (page <= 0) page = 1;
        if(totalPage == 0) totalPage = 1;
        if (page > totalPage) page = totalPage;


        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);

        List<Question> questionList = questionMapper.listByUsersID(usersID, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questionList) {
            Users users = userMapper.findByID(question.getCreator());
            if (users == null) {
                // 如果没有找到用户，使用默认的用户对象或跳过
                users = new Users();
                users.setAvatarUrl("https://assets.leetcode.cn/aliyun-lc-upload/default_avatar.png");  // 设置默认头像
            }
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUsers(users);
            questionDTOList.add(questionDTO);
        };
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getByID(Long id) {
        Question question = questionMapper.findByID(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        Users users = userMapper.findByID(question.getCreator());
        questionDTO.setUsers(users);
        return questionDTO;
    }

    public void insertOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.create(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
            if(question ==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        questionMapper.incrementViewCount(id);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexTag);

        List<Question> questionList = questionMapper.selectRelated(question);
        List<QuestionDTO> questionDTOs = questionList.stream().map(q ->{
             QuestionDTO questionDTO = new QuestionDTO();
             BeanUtils.copyProperties(q, questionDTO);
             return questionDTO;
        }).collect(Collectors.toList());


        return questionDTOs;
    }
}
