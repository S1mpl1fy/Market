package com.example.market1.Controller;

import com.example.market1.Model.Goods;
import com.example.market1.Model.GoodsViewModel;
import com.example.market1.Model.HostHolder;
import com.example.market1.Model.User;
import com.example.market1.Service.GoodsService;
import com.example.market1.Service.TicketService;
import com.example.market1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping("/market/index")
    public String index(HttpServletRequest request, Map<String, Object> map){
        if(hostHolder.getUser() == null){
            return "forward:homepage";
        }

        String page = request.getParameter("page");
        int start = 0, end = 10;
        if(page != null){
            end = Integer.parseInt(page) * 10;
            start = end - 10;
        }
        //System.out.println("/market/index " + start + " " + end);

        List<GoodsViewModel> gvm = new ArrayList<>();
        List<Goods> goodsList = goodsService.getLatestGoods(start, end);
        for(Goods goods: goodsList){
            User user = userService.getUserById(goods.getUserId());
            GoodsViewModel goodsViewModel = new GoodsViewModel(goods,user);
            gvm.add(goodsViewModel);
        }
        map.put("gvm",gvm);
        return "index";
    }
}
