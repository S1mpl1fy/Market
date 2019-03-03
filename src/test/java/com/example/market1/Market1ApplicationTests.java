package com.example.market1;

import com.example.market1.DAO.GoodsDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.Goods;
import com.example.market1.Model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Market1ApplicationTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    GoodsDAO goodsDAO;

    @Test
    public void contextLoads() throws Exception{
        //User user = userDAO.getUserById(6);
        //Goods goods = new Goods("Bike On Sale","I want to sell my bike. Call me.","https://avatar.csdn.net/A/6/F/3_qq_35117601.jpg",149,8,0,0,0,new Date());
        //goodsDAO.addGoods(goods);


            Goods goods= new Goods();
            goods = goodsDAO.getGoodsById(1);
            System.out.println(goods.getUserId());
            System.out.println(goods.getcommentCount());

            User user = userDAO.getUserById(7);
            System.out.println(user.getMail());

    }

}
