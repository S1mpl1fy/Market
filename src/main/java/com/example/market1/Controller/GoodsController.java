package com.example.market1.Controller;

import com.example.market1.Model.*;
import com.example.market1.Service.*;
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

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    TransactionService transactionService;

    @RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = goodsService.saveImage(file);
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
        if(hostHolder.getUser() == null){
            return "forward:login";
        }

        return "publish";
    }

    @RequestMapping("/market/publish.jspy")
    public String publishGoods(@ModelAttribute("form")PublishForm publishForm, HttpServletResponse response, HttpServletRequest request, Model model){
        return goodsService.publishGoods(publishForm, response, request, model);
    }

    @RequestMapping("/market/goods/detail/{goodsId}")
    public String goodsDetailPage(@PathVariable("goodsId") int goodsId, HttpServletRequest request, Model model){
        User localUser = hostHolder.getUser();
        if(localUser == null){
            return "forward:login";
        }

        Goods goods = goodsService.getGoodsById(goodsId);
        User user = userService.getUserById(goods.getUserId());

        //加载商品详情有关的商品和用户信息
        Map<String, Object> gu = new HashMap<>();
        gu.put("goods", goods);
        gu.put("user", user);
        gu.put("status", likeService.getLikeStatus(localUser.getId(), EntityType.ENTITY_GOODS, goodsId));
        model.addAttribute("gu", gu);

        //加载评论
        List<CommentViewModel> cvmList = commentService.getCommentByGoodsId(goodsId);
        model.addAttribute("cus",cvmList);
        return "good_detail";
    }

    @RequestMapping("/market/goods")
    public String index(HttpServletRequest request, Map<String, Object> map){
        if(hostHolder.getUser() == null){
            return "forward:login";
        }
        User localUser = hostHolder.getUser();

        String page = request.getParameter("page");
        int start = 0, end = 10;
        if(page != null){
            end = Integer.parseInt(page) * 10;
            start = end - 10;
        }
        //System.out.println("/market/index " + start + " " + end);

        List<ViewObject> gvm = new ArrayList<>();
        List<Goods> goodsList = goodsService.getLatestGoods(start, end);
        for(Goods goods: goodsList){
            User user = userService.getUserById(goods.getUserId());
            ViewObject viewObject = new ViewObject();
            viewObject.set("user", user);
            viewObject.set("goods", goods);
            viewObject.set("like", likeService.getLikeStatus(localUser.getId(),  EntityType.ENTITY_GOODS, goods.getId()));
            gvm.add(viewObject);
        }
        map.put("gvm",gvm);
        return "goods";
    }

    @RequestMapping("/market/goods/delete/{goodsId}")
    @ResponseBody
    public String deleteGoods(@PathVariable("goodsId") int goodsId, HttpServletRequest request){
        goodsService.deleteGoodsById(goodsId);
        return MarketUtils.getJSONString(0);
    }

    @RequestMapping("/market/goods/deal")
    @ResponseBody
    public String dealGoods(@RequestParam("goodsId") int goodsId, @RequestParam("userId") int userId, HttpServletRequest request){
        int ownerId = goodsService.getGoodsById(goodsId).getUserId();
        int trId = transactionService.addTransation(new Transaction(ownerId, userId, goodsId, goodsService.getGoodsById(goodsId).getPrice(), 0));
        String conversationId = goodsService.dealGoods(userId,ownerId,goodsId,trId);
        if(conversationId == null)
            return MarketUtils.getJSONString(-1);
        return MarketUtils.getJSONString(0, conversationId);
    }

    @RequestMapping("/market/goods/deal/complete")
    @ResponseBody
    public String dealGoods(@RequestParam("trId") int transactionId,  HttpServletRequest request){
        if(transactionService.getTransaction(transactionId).getStatus() == 1)
            return MarketUtils.getJSONString(-1);
        transactionService.updateTransaction(transactionId, 1);
        String conversationId = goodsService.dealGoodsComplete(transactionId);
        if(conversationId == null)
            return MarketUtils.getJSONString(-1);
        return MarketUtils.getJSONString(0, conversationId);
    }

    @RequestMapping("/market/goods/deal/cancel")
    @ResponseBody
    public String cancelDeal(@RequestParam("trId") int transactionId,  HttpServletRequest request){
        if(transactionService.getTransaction(transactionId).getStatus() == 2)
            return MarketUtils.getJSONString(-1);
        transactionService.updateTransaction(transactionId, 2);
        String conversationId = goodsService.cancelGoodsDeal(transactionId);
        if(conversationId == null)
            return MarketUtils.getJSONString(-1);
        return MarketUtils.getJSONString(0, conversationId);
    }
}
