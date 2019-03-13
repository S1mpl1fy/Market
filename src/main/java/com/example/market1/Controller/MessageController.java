package com.example.market1.Controller;

import com.example.market1.Model.*;
import com.example.market1.Service.MessageService;
import com.example.market1.Service.TransactionService;
import com.example.market1.Service.UserService;
import com.example.market1.Utils.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/market/msg/list"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model) {
        if(hostHolder.getUser() == null){
            return "forward:login";
        }
        try {
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                User user = userService.getUserById(targetId);
                vo.set("user", user);
                vo.set("unread", messageService.getConversationUnreadCount(localUserId, msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            System.out.println("获取站内信列表失败" + e.getMessage());
        }
        return "message";
    }

    @RequestMapping(path = {"/market/msg/detail/{conversationId}"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @PathVariable("conversationId") String conversationId) {
        if(hostHolder.getUser() == null){
            return "forward:login";
        }
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUserById(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("fromId", user.getId());
                vo.set("toId", msg.getToId());
                if(msg.getExt() != null){
                    vo.set("ext", msg.getExt());
                    vo.set("transaction", transactionService.getTransaction(Integer.parseInt(msg.getExt())));
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                vo.set("userName", user.getName());
                messages.add(vo);
            }
            int localUserId = hostHolder.getUser().getId();
            String[] temp = conversationId.split("_");
            String targetId = String.valueOf(localUserId).equals(temp[0])?temp[1]:temp[0];

            model.addAttribute("targetId", targetId);
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            System.out.println("获取详情消息失败" + e.getMessage());
        }
        return "message_detail";
    }


    @RequestMapping(path = {"/market/msg/list.add"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try{
            String conversationId = messageService.addMessage(fromId, toId, content);
            return MarketUtils.getJSONString(0,conversationId);
        }catch (Exception e){
            return MarketUtils.getJSONString(-1,"");
        }
    }
}
