package com.example.market1.Utils;

import com.example.market1.Model.TicketLogin;
import org.springframework.beans.factory.annotation.Autowired;
import sun.security.krb5.internal.Ticket;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class MarketUtils {

    public static String getTicketFromRequst(HttpServletRequest request){
        String ticket  = null;
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        return ticket;
    }
}
