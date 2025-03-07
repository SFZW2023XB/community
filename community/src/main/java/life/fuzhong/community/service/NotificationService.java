package life.fuzhong.community.service;

import jakarta.annotation.Resource;
import life.fuzhong.community.dto.NotificationDTO;
import life.fuzhong.community.dto.PaginationDTO;
import life.fuzhong.community.enums.NotificationEnum;
import life.fuzhong.community.enums.NotificationStatusEnum;
import life.fuzhong.community.exception.CustomizeErrorCode;
import life.fuzhong.community.exception.CustomizeException;
import life.fuzhong.community.mapper.NotificationMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Notification;
import life.fuzhong.community.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
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
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }

    public Long unreadCount(Long usersId) {
        return notificationMapper.countUnreadByUsersID(usersId);

    }

    public NotificationDTO read(Long id, Users users) {
        Notification notification = notificationMapper.selectById(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(), users.getId())){
            throw new CustomizeException(CustomizeErrorCode.CHECK_OTHERS_EXCEPTION);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateStatusById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
