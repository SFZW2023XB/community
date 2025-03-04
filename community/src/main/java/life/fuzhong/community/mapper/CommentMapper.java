package life.fuzhong.community.mapper;

import life.fuzhong.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_modified,gmt_create,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtModified},#{gmtCreate},#{likeCount},#{content})")
    void create(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment selectById(Long id);

    @Select("select * from comment where parent_id = #{id} and type = 1")
    List<Comment> selectByQuestionId(Long id);

    @Select("select * from comment where parent_id = #{id} and type = 2")
    List<Comment> selectByCommentId(Long id);
}
