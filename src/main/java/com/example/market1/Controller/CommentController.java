package com.example.market1.Controller;

import com.example.market1.Service.CommentService;
import com.example.market1.Utils.MarketUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping("market/comment/submit")
    @ResponseBody
    public String submitComment(HttpServletRequest request, Model model){
        String userId = request.getParameter("userId"), goodsId = request.getParameter("goodsId"), content = request.getParameter("content");
        if(userId == null || goodsId == null || content == null){
            return MarketUtils.getJSONString(-1,"false");
        }
        commentService.addComment(Integer.parseInt(userId), Integer.parseInt(goodsId), content, new Date());

        return MarketUtils.getJSONString(0,"ok");
    }

}
