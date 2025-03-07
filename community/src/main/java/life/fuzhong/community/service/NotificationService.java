package life.fuzhong.community.service;

import jakarta.annotation.Resource;
import life.fuzhong.community.dto.NotificationDTO;
import life.fuzhong.community.dto.PaginationDTO;
import life.fuzhong.community.enums.NotificationEnum;
import life.fuzhong.community.mapper.NotificationMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Notification;
import life.fuzhong.community.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private UserMapper userMapper;

    public PaginationDTO list(Long usersID, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Long totalCount = notificationMapper.countByUsersID(usersID);

        Integer totalPage = (int) (totalCount + size - 1) / size;
        if (page <= 0) page = 1;
        if(totalPage == 0) totalPage = 1;
        if (page > totalPage) page = totalPage;


        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);

        List<Notification> notificationList = notificationMapper.listByUsersID(usersID, offset, size);
        if(notificationList.size() == 0) return paginationDTO;



        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setType(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }

    public Long unreadCount(Long usersId) {
        return notificationMapper.countByUsersID(usersId);

    }
}
