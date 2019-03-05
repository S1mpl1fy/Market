package com.example.market1.DAO;

import com.example.market1.Model.Ticket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TicketDAO {
    String TABLE_NAME = "ticket";
    String INSERT_FIELDS = "userid, expired, status, ticket";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELDS, ") values (#{userid}, #{expired}, #{status}, #{ticket})"})
    int addTicket(Ticket ticket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    Ticket getTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status = 1 where ticket = #{ticket}" })
    int expireTicket(String ticket);

}
