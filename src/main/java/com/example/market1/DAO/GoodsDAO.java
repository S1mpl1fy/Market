package com.example.market1.DAO;

import com.example.market1.Model.Goods;
import com.example.market1.Model.User;
import org.apache.ibatis.annotations.*;

import javax.validation.constraints.Size;
import java.util.List;

@Mapper
public interface GoodsDAO {
    String TABLE_NAME = "goods";
    String INSERT_FIELDS = "title, description, image, price, user_id, status, comment_count, like_count, created_date";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title}, #{description}, #{image}, #{price}, #{userId}, #{status}, #{commentCount}, #{likeCount}, #{createdDate})"})
    int addGoods(Goods goods);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Goods getGoodsById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where userid = #{userid} limit #{start}, #{end}"})
    List<Goods> getPersonalGoods(@Param("userid") int userid, @Param("start") int start, @Param("end") int end);

    @Select({"select count(*) from ", TABLE_NAME})
    Goods getGoodsCount();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " order by id desc limit #{start}, #{end}"})
    List<Goods> getLatestGoods(@Param("start") int start, @Param("end") int end);

    @Update({"update ", TABLE_NAME, " set comment_count = comment_count + 1 where id = #{id}"})
    int updateCommentCount(int id);

    @Update({"update ", TABLE_NAME, " set like_count = #{likeCount} where id = #{goodsId}"})
    int updateLikeCount(@Param("goodsId") int goodsId, @Param("likeCount") int likeCount);

}
