package life.fuzhong.community.mapper;

import life.fuzhong.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Select("select * from question limit #{size} offset #{offset}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title}, #{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create (Question question);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{usersID} limit #{size} offset #{offset}")
    List<Question> listByUsersID(@Param("usersID") Integer usersID,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select  count (1) from question where creator = #{usersID}")
    Integer countByUsersID(@Param("usersID") Integer usersID);

    @Select("select * from question where id = #{id}")
    Question findByID(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, tag = #{tag}, gmt_modified = #{gmtModified} where id = #{id}")
    void update(Question question);
}
