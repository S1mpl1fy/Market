package com.example.market1.Model;

import java.util.Date;

public class Message {
    int id;
    int fromId;
    int toId;
    String conversationId;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    String ext;

    public Message(){}

    public Message(int fromId, int toId, String content, Date createdDate, String conversationId, String ext){
        this.fromId = fromId;
        this.toId = toId;
        this.createdDate = createdDate;
        this.content = content;
        this.conversationId = conversationId;
        this.ext = ext;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    int hasRead;
    String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    Date createdDate;
}
