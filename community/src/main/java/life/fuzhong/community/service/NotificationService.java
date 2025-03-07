package life.fuzhong.community.service;

import jakarta.annotation.Resource;
import life.fuzhong.community.dto.NotificationDTO;
import life.fuzhong.community.dto.PaginationDTO;
import life.fuzhong.community.mapper.NotificationMapper;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Notification;
import life.fuzhong.community.model.Users;
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
        Integer totalCount = notificationMapper.countByUsersID(usersID);

        Integer totalPage = (totalCount + size - 1) / size;
        if (page <= 0) page = 1;
        if(totalPage == 0) totalPage = 1;
        if (page > totalPage) page = totalPage;


        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);

        List<Notification> notificationList = notificationMapper.listByUsersID(usersID, offset, size);
        if(notificationList.size() == 0) return paginationDTO;

        Set<Long> disusesIds = notificationList.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
        List<Long> usersIds = new ArrayList<>(disusesIds);
        List<Users> usersList = userMapper.selectAllUsers(usersIds);

        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        Map<Long, Users> usersMap = usersList.stream().collect(Collectors.toMap(u -> u.getId(), u ->u));

        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }
}
