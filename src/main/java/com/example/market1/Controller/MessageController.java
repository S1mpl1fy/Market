package com.example.market1.Controller;

import com.example.market1.Model.HostHolder;
import com.example.market1.Model.Message;
import com.example.market1.Model.User;
import com.example.market1.Model.ViewObject;
import com.example.market1.Service.MessageService;
import com.example.market1.Service.UserService;
import com.example.market1.Utils.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/market/msg/list"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model) {
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
                vo.set("unread", messageService.getConvesationUnreadCount(localUserId, msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            System.out.println("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/market/msg/detail/{conversationId}"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @PathVariable("conversationId") String conversationId) {
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
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            System.out.println("获取详情消息失败" + e.getMessage());
        }
        return "letter_detail";
    }


    @RequestMapping(path = {"/market/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message msg = new Message();
            msg.setContent(content);
            msg.setFromId(fromId);
            msg.setToId(toId);
            msg.setCreatedDate(new Date());
            msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.addMessage(msg);
            return MarketUtils.getJSONString(msg.getId());
        } catch (Exception e) {
            System.out.println("增加评论失败" + e.getMessage());
            return MarketUtils.getJSONString(1, "插入评论失败");
        }
    }
}
