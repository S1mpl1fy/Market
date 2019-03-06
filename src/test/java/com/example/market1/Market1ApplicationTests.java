package com.example.market1;

import com.alibaba.fastjson.JSON;
import com.example.market1.Service.CommentService;
import com.example.market1.Service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Market1ApplicationTests {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    CommentService commentService;

    @Autowired
    GoodsService goodsService;

    @Test
    public void contextLoads() throws Exception{

        /*
        DataSource dataSource = applicationContext.getBean(DataSource.class);

        System.out.println(dataSource);
        System.out.println(dataSource.getClass().getName());
        System.out.println(dataSourceProperties);
        //执行SQL,输出查到的数据
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<?> resultList = jdbcTemplate.queryForList("select * from user");
        System.out.println("===>>>>>>>>>>>" + JSON.toJSONString(resultList));
       */
    }

}
