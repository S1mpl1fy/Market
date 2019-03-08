package com.example.market1.Controller;

import com.example.market1.Model.*;
import com.example.market1.Service.TicketService;
import com.example.market1.Service.UserService;
import com.example.market1.Utils.MarketUtils;
import com.example.market1.Utils.MyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    UserService userService;

    @Autowired
    TicketService ticketService;

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
            if(userService.getUserByMail(userForm.getMail()) != null){
                map.put("mailMsg","邮箱已被占用，请换一个邮箱");
                return "register";
            }
            Random random = new Random();
            String head_url = String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(100));
            String salt = String.format("%f",random.nextDouble());
            pass = MyTools.getMD5(String.format("%s%s", pass, salt));
            userService.addUser(new User(userForm.getName(),mail,pass,salt,head_url,userForm.getSex()));

            map.put("name",mail);
            map.put("head_url",head_url);
            return "forward:goods";
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
            String salt = userService.getSaltByMail(mail);
            String savePass = userService.getPassByMail(mail);
            String mdPass = MyTools.getMD5(pass + salt);
            if(!mdPass.equals(savePass)){
                return "homepage";
            }
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*120);

            Ticket ticket = new Ticket();
            ticket.setUserid(userService.getIdByMail(mail));
            ticket.setExpired(date);
            ticket.setTicket(UUID.randomUUID().toString().replace("-",""));

            ticketService.addTicket(ticket);
            Cookie cookie = new Cookie("ticket", ticket.getTicket());
            cookie.setPath("/market");
            response.addCookie(cookie);

            user = userService.getUserByMail(mail);
            map.put("name", mail);
            map.put("head_url", user.getHeadUrl());
            return "homepage";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping("user/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response){
        String ticket = MarketUtils.getTicketFromRequst(request);
        Cookie cookie = new Cookie("ticket",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        if(ticket != null){
            ticketService.updateStatus(ticket);
        }
        return MarketUtils.getJSONString(0,"logout.");
    }

    @RequestMapping("/user/{id}")
    public String personalPage(@PathVariable("id") int id, HttpServletRequest request, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("target", user);
        return "personal_page";
    }
}
