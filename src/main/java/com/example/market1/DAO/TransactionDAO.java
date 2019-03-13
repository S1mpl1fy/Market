package com.example.market1.DAO;

import com.example.market1.Model.Ticket;
import com.example.market1.Model.Transaction;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TransactionDAO {

    String TABLE_NAME = "transaction";
    String INSERT_FIELDS = "owner_id, buy_id, goods_id, price, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELDS, ") values (#{ownerId}, #{buyId}, #{goodsId}, #{price},#{status})"})
    int addTransaction(Transaction transaction);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Transaction getTransaction(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where goods_id = #{goodsId}"})
    Transaction getTransactionByGoods(int goodsId);

    @Update({"update ", TABLE_NAME, " set status = #{status} where id = #{id}" })
    int updateStatus(@Param("id")int id, @Param("status") int status);



}
