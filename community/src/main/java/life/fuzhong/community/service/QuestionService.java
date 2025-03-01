package life.fuzhong.community.service;

import jakarta.annotation.Resource;
import life.fuzhong.community.dto.PaginationDTO;
import life.fuzhong.community.dto.QuestionDTO;
import life.fuzhong.community.mapper.QuestionMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Question;
import life.fuzhong.community.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public PaginationDTO list(Integer usersID, Integer page, Integer size) {
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

    public QuestionDTO getByID(Integer id) {
        Question question = questionMapper.findByID(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        Users users = userMapper.findByID(question.getCreator());
        questionDTO.setUsers(users);
        return questionDTO;
    }
}
