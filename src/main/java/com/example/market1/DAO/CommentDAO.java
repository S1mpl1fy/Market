package com.example.market1.DAO;

import com.example.market1.Model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "user_id, goods_id, status, content, comment_time";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId}, #{goodsId}, #{status}, #{content}, #{commentTime})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where goods_id=#{goodsId}"})
    List<Comment> getCommentByGoodsId(int goodsId);

}
