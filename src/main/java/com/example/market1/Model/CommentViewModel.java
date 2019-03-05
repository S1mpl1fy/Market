package com.example.market1.Model;

import java.util.Date;

public class CommentViewModel {
    public String content, userName, userHeadUrl;
    public Date date;

    public CommentViewModel(){}

    public CommentViewModel(String content, String userName, String userHeadUrl, Date date){
        this.content = content;
        this.userName = userName;
        this.userHeadUrl = userHeadUrl;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
