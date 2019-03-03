package com.example.market1.Controller;

import com.example.market1.DAO.GoodsDAO;
import com.example.market1.DAO.TicketUserDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.Goods;
import com.example.market1.Model.GoodsViewModel;
import com.example.market1.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    TicketUserDAO ticketUserDAO;

    @Autowired
    GoodsDAO goodsDAO;

    @RequestMapping("/market/index")
    public String index(HttpServletRequest request, Map<String, Object> map){

        String page = request.getParameter("page");
        int start = 0, end = 10;
        if(page != null){
            end = Integer.parseInt(page) * 10;
            start = end - 10;
        }
        System.out.println("/market/index " + start + " " + end);

        List<GoodsViewModel> gvm = new ArrayList<>();
        List<Goods> goodsList = goodsDAO.getLatestGoods(start, end);
        for(Goods goods: goodsList){
            User user = userDAO.getUserById(goods.getUserId());
            GoodsViewModel goodsViewModel = new GoodsViewModel(goods,user);
            //System.out.println(goods.getTitle());
            //System.out.println(user.getMail());
            gvm.add(goodsViewModel);
        }
        map.put("gvm",gvm);
        //model.addAttribute("gvm", gvm);
        return "index2";
    }
}
