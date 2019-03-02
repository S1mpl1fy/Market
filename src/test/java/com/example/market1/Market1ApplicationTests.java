package com.example.market1;

import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.User;
import com.example.market1.Utils.MyTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.rmi.server.ExportException;
import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Market1ApplicationTests {
    @Autowired
    UserDAO userDAO;

    @Test
    public void contextLoads() throws Exception{

        Date date = new Date();
        MyTools.log(date.toString());
    }

}
