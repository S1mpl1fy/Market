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

    public int addUser(User user){
        return userDAO.addUser(user);
    }

    public User getUserById(int id){
        return userDAO.getUserById(id);
    }

    public User getUserByMail(String mail){
        return userDAO.getUserByMail(mail);
    }

    public String getSaltByMail(String mail){
        return userDAO.getSaltByMail(mail);
    }

    public String getPassByMail(String mail){
        return userDAO.getPassByMail(mail);
    }

    public int getIdByMail(String mail){
        return userDAO.getIdByMail(mail);
    }
}
