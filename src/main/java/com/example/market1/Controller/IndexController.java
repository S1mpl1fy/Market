package com.example.market1.Controller;

import com.example.market1.DAO.TicketUserDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.TicketLogin;
import com.example.market1.Utils.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    TicketUserDAO ticketUserDAO;

    @RequestMapping("/market/index")
    public String index(HttpServletRequest request, Map<String, Object> map){
        return "index";
    }
}
