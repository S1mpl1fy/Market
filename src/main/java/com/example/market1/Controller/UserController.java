package com.example.market1.Controller;

import com.example.market1.DAO.TicketUserDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.LoginForm;
import com.example.market1.Model.TicketLogin;
import com.example.market1.Model.User;
import com.example.market1.Model.UserForm;
import com.example.market1.Utils.MarketUtils;
import com.example.market1.Utils.MyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/market")
public class UserController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    TicketUserDAO ticketUserDAO;

    @RequestMapping("/register")
    public String registerPage(Map<String, Object> map){
        map.put("name","未登录");
        map.put("passMsg","请输入");
        map.put("mailMsg","请输入");
        return "register";
    }

    @RequestMapping("/register.jspy")
    public String register(@ModelAttribute("form")UserForm userForm, Map<String, Object> map) throws Exception{
        try{
            if(userForm.getSex() == 1) System.out.println(111111111);
            //System.out.println();
            String mail = userForm.getMail();
            String pass = userForm.getPass();
            if(! userForm.getPass().equals(userForm.getPass2())){
                map.put("passMsg","两次输入的密码不相同");
                return "register";
            }
            if(userDAO.getUserByMail(userForm.getMail()) != null){
                map.put("mailMsg","邮箱已被占用，请换一个邮箱");
                return "register";
            }
            Random random = new Random();
            String head_url = String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(100));
            String salt = String.format("%f",random.nextDouble());
            pass = MyTools.getMD5(String.format("%s%s", pass, salt));
            userDAO.addUser(new User(userForm.getName(),mail,pass,salt,head_url,userForm.getSex()));
            //map.put("mail", userForm.getMail());
            map.put("name",mail);
            map.put("head_url",head_url);
            return "forward:index";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping("/homepage")
    public String login(Map<String, Object> map, HttpServletRequest request){
        map.put("name", "未登录");
        map.put("passMsg", "请输入");
        return "homepage";
    }


    @RequestMapping(value = "/homepage.jspy",method = {RequestMethod.POST})
    public String loginSubmit(@ModelAttribute("form")LoginForm loginForm, Map<String, Object> map, HttpServletResponse response, HttpServletRequest request)throws Exception{
        try{
            System.out.println("homepage.jspy");
            User user;
            String mail = loginForm.getMail(), pass = loginForm.getPass();
            String salt = userDAO.getSaltByMail(mail);
            String savePass = userDAO.getPassByMail(mail);
            if(salt == null){
                map.put("name", "未登录");
                map.put("passMsg", "用户不存在");
                return "homepage";
            }
            String mdPass = MyTools.getMD5(pass + salt);
            if(!mdPass.equals(savePass)){
                map.put("name", "未登录");
                map.put("passMsg", "密码错误");
                return "homepage";
            }
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*120);
            //System.out.println(date);

            String ticket = MarketUtils.getTicketFromRequst(request);
            System.out.println("login ticket:!!" + ticket);

            TicketLogin ticketLogin = new TicketLogin();
            ticketLogin.setUserid(userDAO.getIdByMail(mail));
            ticketLogin.setExpired(date);
            ticketLogin.setTicket(UUID.randomUUID().toString().replace("-",""));

            ticketUserDAO.addTicketLogin(ticketLogin);
            Cookie cookie = new Cookie("ticket", ticketLogin.getTicket());
            cookie.setPath("/market");
            response.addCookie(cookie);

            user = userDAO.getUserByMail(mail);
            map.put("name", mail);
            map.put("head_url", user.getHeadUrl());
            //response.get
            return "forward:index";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping("user/logout")
    public String logout(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response){
        String ticket = MarketUtils.getTicketFromRequst(request);
        //if(ticket != null) System.out.println(ticket);
        //else System.out.println("12345678+++++++++");
        Cookie cookie = new Cookie("ticket",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        if(ticket != null){
            ticketUserDAO.updateStatus(ticket);
        }
        map.put("logout","1");
        return "index";
    }
}
