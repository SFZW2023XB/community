package life.fuzhong.community.mapper;

import life.fuzhong.community.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier,receiver,outerid,type,gmt_create,status) valus (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{create})")
    void create(Notification notification);

    @Select("select count(1) from notification where notifier = #{usersID}")
    Integer countByUsersID(Long usersID);

    @Select("select * from notification where notifier = #{usersID} limit #{size} offset #{offset}")
    List<Notification> listByUsersID(Long usersID, Integer offset, Integer size);
}
