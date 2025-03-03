package life.fuzhong.community.mapper;

import life.fuzhong.community.model.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into users (name,account_id,token,gmt_create,gmt_modified,avatar_url,bio) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl}," + "COALESCE(#{bio}, '') )" )
    void insert(Users users);

    @Select("select * from users where token = #{token}")
    Users findBytoken(@Param("token") String token);

    @Select("select * from users where id = #{id}")
    Users findByID(@Param("id") Long id);

    @Select("select * from users where account_id = #{accountId}")
    Users findByAccountID(@Param("accountId") String accountId);

    @Update("update users set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(Users users);
}
