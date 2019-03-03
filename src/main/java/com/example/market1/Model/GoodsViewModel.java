package com.example.market1.Model;

import java.util.HashMap;
import java.util.Map;

public class GoodsViewModel {

    private Goods goods;
    public GoodsViewModel(){}

    public GoodsViewModel(Goods goods, User user){
        this.goods = goods;
        this.user = user;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;
}
