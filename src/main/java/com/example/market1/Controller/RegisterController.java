package com.example.market1.Controller;

import com.example.market1.Model.User;
import com.example.market1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/market/user")
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
}
