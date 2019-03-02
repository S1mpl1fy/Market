package com.example.market1.DAO;

import ch.qos.logback.classic.db.names.TableName;
import com.example.market1.Model.TicketLogin;
import com.example.market1.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import sun.security.krb5.internal.Ticket;

@Mapper
public interface TicketUserDAO {
    String TABLE_NAME = "ticket_login";
    String INSERT_FIELDS = "userid, expired, status, ticket";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELDS, ") values (#{userid}, #{expired}, #{status}, #{ticket})"})
    int addTicketLogin(TicketLogin ticketLogin);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    TicketLogin getTicketLogin(String ticket);

    @Update({"update ", TABLE_NAME, " set status = 1 where ticket = #{ticket}" })
    int updateStatus(String ticket);

}
