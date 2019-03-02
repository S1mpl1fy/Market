package com.example.market1.Model;

public class User {
    private int id, sex;

    public User(){

    }
    public User(String name, String mail, String password, String salt, String headUrl, int sex){
        //this.id = 0;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.sex = sex;
        this.headUrl = headUrl;
        this.mail = mail;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private String name, mail, password, headUrl, salt;
}
