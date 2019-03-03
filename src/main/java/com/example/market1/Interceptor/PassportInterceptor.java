package com.example.market1.Interceptor;

import com.example.market1.DAO.TicketUserDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.HostHolder;
import com.example.market1.Model.TicketLogin;
import com.example.market1.Model.User;
import com.example.market1.Utils.MarketUtils;
import com.example.market1.Utils.MyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TicketUserDAO ticketUserDAO;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = MarketUtils.getTicketFromRequst(request);
        MyTools.log("ticket:" + ticket + "$" );
        if(ticket != null){
            TicketLogin ticketLogin = ticketUserDAO.getTicketLogin(ticket);

            /*
            if(ticketLogin == null){
                System.out.println("ticketLogin is null.");
            }
            if(ticketLogin.getExpired().before(new Date())){
                System.out.println("Expired.");
            }
            if(ticketLogin.getStatus() == 1){
                System.out.println("ticket's status is 1.");
            }
             */
            if(ticketLogin == null || ticketLogin.getExpired().before(new Date()) || ticketLogin.getStatus() == 1){
                return true;
            }
            //MyTools.log("Ticketlogin in use.");
            User user = userDAO.getUserById(ticketLogin.getUserid());
            MyTools.log("User got by ticket:" + user.getMail());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*
        String ticket = MarketUtils.getTicketFromRequst(request);
        if(ticket!= null && getTicketStatus(ticket)==1){
            modelAndView.
        }
         */
        if(modelAndView != null && hostHolder.getUser() != null){
            modelAndView.addObject("user", hostHolder.getUser());
            System.out.println("modelAndView added user already.");
        }
       else{
           if(hostHolder.getUser()== null)
               System.out.println("hostholder.getUser get null.");
           System.out.println("modelAndView didn't add user.");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }

    /*
    public int getTicketStatus(String ticket){
        TicketLogin ticketLogin = ticketUserDAO.getTicketLogin(ticket);
        return ticketLogin.getStatus();
    }
     */
}
