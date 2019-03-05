package com.example.market1.Controller;

import com.example.market1.Model.*;
import com.example.market1.Service.CommentService;
import com.example.market1.Service.GoodsService;
import com.example.market1.Service.TicketService;
import com.example.market1.Service.UserService;
import com.example.market1.Utils.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = goodsService.saveImage(file);
            //String fileUrl = qiniuService.saveImage(file);
            if (fileUrl == null) {
                return MarketUtils.getJSONString(1, "上传图片失败");
            }
            return MarketUtils.getJSONString(0, fileUrl);
        } catch (Exception e) {
            System.out.println("上传图片失败" + e.getMessage());
            return MarketUtils.getJSONString(1, "上传失败");
        }
    }

    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new
                    File(MarketUtils.IMAGE_DIR + imageName)), response.getOutputStream());
        } catch (Exception e) {
            System.out.println("读取图片错误" + imageName + e.getMessage());
        }
    }

    @RequestMapping("/market/publish")
    public String publishPage(){
        return "publish";
    }

    @RequestMapping("/market/publish.jspy")
    public String publishGoods(@ModelAttribute("form")PublishForm publishForm, HttpServletResponse response, HttpServletRequest request, Model model){
        return goodsService.publishGoods(publishForm, response, request, model);
        /*
        String ticket = MarketUtils.getTicketFromRequst(request);
        int userId = 0;
        if(ticket == null) return "homepage";
        Ticket ticketLogin = ticketUserDAO.getTicket(ticket);
        if(!MarketUtils.ticketLoginValid(ticketLogin)){
            return "homepage";
        }else{
            userId = ticketLogin.getUserid();
        }
        String url = request.getParameter("image");
        String url2 = publishForm.getImage();
        if(publishForm.getImage() == null){
            return "forward:index";
        }
        Goods goods = new Goods(publishForm.getTitle(),publishForm.getDescription(),publishForm.getImage(), userId, publishForm.getPrice(), 0, 0, 0, new Date());
        goodsService.addGoods(goods);
        return "forward:index";
         */
    }

    @RequestMapping("/market/goods/detail/{goodsId}")
    public String goodsDetailPage(@PathVariable("goodsId") int goodsId, HttpServletRequest request, Model model){
        Goods goods = goodsService.getGoodsById(goodsId);
        User user = userService.getUserById(goods.getUserId());

        //加载商品详情有关的商品和用户信息
        Map<String, Object> gu = new HashMap<>();
        gu.put("goods", goods);
        gu.put("user", user);
        model.addAttribute("gu", gu);

        //加载评论
        List<CommentViewModel> cvmList = commentService.getCommentByGoodsId(goodsId);
        model.addAttribute("cus",cvmList);
        return "good_detail";
    }
}
