package com.example.market1.Model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    public  User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear(){
        users.remove();
    }

    private static ThreadLocal<User> users = new ThreadLocal<>();
}
