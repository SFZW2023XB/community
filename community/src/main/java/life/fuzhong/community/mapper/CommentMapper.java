package life.fuzhong.community.mapper;

import life.fuzhong.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_modified,gmt_create,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtModified},#{gmtCreate},#{likeCount},#{content})")
    void create(Comment comment);

    @Select("select * form comment where id = #{id}")
    Comment selectById(Long id);
}
