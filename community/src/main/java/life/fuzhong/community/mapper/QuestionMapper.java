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
    List<Question> listByUsersID(@Param("usersID") Long usersID,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select  count (1) from question where creator = #{usersID}")
    Integer countByUsersID(@Param("usersID") Long usersID);

    @Select("select * from question where id = #{id}")
    Question findByID(@Param("id") Long id);

    @Update("update question set title = #{title}, description = #{description}, tag = #{tag}, gmt_modified = #{gmtModified} where id = #{id}")
    void update(Question question);

    @Update("update question set view_count = #{viewCount}  where id = #{id}")
    void updateView(Question question);

    @Update("update question set comment_count = #{commentCount} where id = #{id}")
    void updateCommentCount(Question question);

    @Update("UPDATE question SET view_count = view_count + 1 WHERE id = #{id};")
    void incrementViewCount(Long id);

    @Update("UPDATE question SET comment_count = comment_count + 1 WHERE id = #{id};")
    void incCommentCount(Question question);
}
