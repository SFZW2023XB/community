package life.fuzhong.community.mapper;

import life.fuzhong.community.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("INSERT INTO notification (notifier, receiver, outerid, type, gmt_create, status,notifier_name,outer_title) VALUES (#{notifier}, #{receiver}, #{outerId}, #{type}, #{gmtCreate}, #{status},#{notifierName},#{outerTitle})")
    void create(Notification notification);

    @Select("select count(1) from notification where receiver = #{usersID}")
    Long countByUsersID(Long usersID);

    @Select("select * from notification where receiver = #{usersID} limit #{size} offset #{offset}")
    List<Notification> listByUsersID(Long usersID, Integer offset, Integer size);


}
