package life.fuzhong.community.mapper;

import life.fuzhong.community.dto.NotificationDTO;
import life.fuzhong.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("INSERT INTO notification (notifier, receiver, outerid, type, gmt_create, status,notifier_name,outer_title) VALUES (#{notifier}, #{receiver}, #{outerid}, #{type}, #{gmtCreate}, #{status},#{notifierName},#{outerTitle})")
    void create(Notification notification);

    @Select("select count(1) from notification where receiver = #{usersID} and status = 0")
    Long countUnreadByUsersID(Long usersID);

    @Select("select * from notification where receiver = #{usersID} ORDER BY gmt_create DESC limit #{size} offset #{offset}")
    List<Notification> listByUsersID(Long usersID, Integer offset, Integer size);


    @Select("select * from notification where id = #{id}")
    Notification selectById(Long id);

    @Update("update notification set status = #{status} where id = #{id}")
    void updateStatusById(Notification notification);

    @Select("select count(1) from notification where receiver = #{usersID}")
    Long countByUsersID(Long usersID);
}
