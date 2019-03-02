package com.example.market1.DAO;

import com.example.market1.Model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name, mail, password, sex, head_url, salt";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{name}, #{mail}, #{password}, #{sex}, #{headUrl}, #{salt})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User getUserById(int id);

    @Select({"select name from user where id = #{id} "})
    String getNameById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where mail=#{mail}"})
    User getUserByMail(String mail);

    @Select({"select salt from ", TABLE_NAME, " where mail=#{mail}"})
    String getSaltByMail(String mail);

    @Select({"select password from ", TABLE_NAME, " where mail=#{mail}"})
    String getPassByMail(String mail);

    @Select({"select id from ", TABLE_NAME, " where mail=#{mail}"})
    int getIdByMail(String mail);
    /*
    @Update({"update ", TABLE_NAME, " set password = #{password} where id=#{id} "})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id} "})
    void deleteById(int id);
     */
}
