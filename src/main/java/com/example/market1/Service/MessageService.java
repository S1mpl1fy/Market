package com.example.market1.Service;

import com.example.market1.DAO.MessageDAO;
import com.example.market1.Model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;

    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }

    public String addMessage(int fromId, int toId, String content){{
        try {
            Message msg = new Message(fromId, toId, content, new Date(), (10 < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId)),"");
            messageDAO.addMessage(msg);
            return String.valueOf(msg.getConversationId());
        } catch (Exception e) {
            System.out.println("增加评论失败" + e.getMessage());
            return null;
        }}
    }

    public String addMessage(int fromId, int toId, String content, String conversationId, String ext){{
        try {
            Message msg = new Message(fromId, toId, content, new Date(), conversationId, ext);
            messageDAO.addMessage(msg);
            return String.valueOf(msg.getConversationId());
        } catch (Exception e) {
            System.out.println("增加评论失败" + e.getMessage());
            return null;
        }}
    }
}
