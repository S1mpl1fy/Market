package com.example.market1.Controller;

import com.example.market1.Model.User;
import com.example.market1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market/user")
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
}
