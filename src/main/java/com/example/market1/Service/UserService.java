package com.example.market1.Service;

import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public int RegisterUser(String name, String mail, String password, String head_url, String salt, int sex ){
        User existUser = userDAO.getUserByMail(mail);
        if(existUser != null){
            return -1;//-1 means mail was used
        }
        User user = new User(name, mail, password, head_url, salt, sex);

        userDAO.addUser(user);
        return 1;
    }

    public User getUser(int id){
        return userDAO.getUserById(id);
    }
}
