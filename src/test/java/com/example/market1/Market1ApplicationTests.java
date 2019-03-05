package com.example.market1;

import com.example.market1.DAO.GoodsDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.Comment;
import com.example.market1.Model.Goods;
import com.example.market1.Model.User;
import com.example.market1.Service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Market1ApplicationTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    GoodsDAO goodsDAO;

    @Autowired
    CommentService commentService;

    @Test
    public void contextLoads() throws Exception{

        //commentService.addComment(8,44,"这个还可以？？");


    }

}
