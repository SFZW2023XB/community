package life.fuzhong.community.mapper;

import life.fuzhong.community.model.Comment;
import life.fuzhong.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_modified,gmt_create,like_count,content, comment_count) values (#{parentId},#{type},#{commentator},#{gmtModified},#{gmtCreate},#{likeCount},#{content},#{commentCount})")
    void create(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment selectById(Long id);

    @Select("select * from comment where parent_id = #{id} and type = 1")
    List<Comment> selectByQuestionId(Long id);

    @Select("select * from comment where parent_id = #{id} and type = 2")
    List<Comment> selectByCommentId(Long id);

    @Update("UPDATE comment SET comment_count = comment_count + 1 WHERE id = #{id};")
    void incCommentCount(Comment comment);
}
