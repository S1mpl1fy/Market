package com.example.market1.Model;

import java.sql.Timestamp;
import java.util.Date;

public class Goods {
    private String title, description,image;
    private int id;
    private int userId;
    private int status;
    private int commentCount;
    private int likeCount;

    public Goods(){}

    public Goods(String title, String  description, String  image, int userId, int price, int status, int commentCount, int likeCount, Date createdDate){
        //this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.userId = userId;
        this.status = status;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
    }

    public Goods(String title, String  description, String  image, int price, int userId, int status, int commentCount, int likeCount, Timestamp createdDate){
        //this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.userId = userId;
        this.status = status;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.createdDate = new Date(createdDate.getTime());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getcommentCount() {
        return commentCount;
    }

    public void setcommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getlikeCount() {
        return likeCount;
    }

    public void setlikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    private int price;
    private Date createdDate;
}
