package com.example.market1.Controller;

import com.example.market1.DAO.GoodsDAO;
import com.example.market1.DAO.TicketUserDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.Goods;
import com.example.market1.Model.PublishForm;
import com.example.market1.Model.TicketLogin;
import com.example.market1.Model.User;
import com.example.market1.Service.GoodsService;
import com.example.market1.Utils.MarketUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    GoodsDAO goodsDAO;

    @Autowired
    TicketUserDAO ticketUserDAO;

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
        String ticket = MarketUtils.getTicketFromRequst(request);
        int userId = 0;
        if(ticket == null) return "homepage";
        TicketLogin ticketLogin = ticketUserDAO.getTicketLogin(ticket);
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
        //request.getParameter("img");
    }

    @RequestMapping("/market/goods/detail/{goodsId}")
    public String goodsDetailPage(@PathVariable("goodsId") int goodsId, HttpServletRequest request, Model model){
        Goods goods = goodsDAO.getGoodsById(goodsId);
        User user = userDAO.getUserById(goods.getUserId());
        Map<String, Object> gu = new HashMap<>();
        gu.put("goods", goods);
        gu.put("user", user);
        model.addAttribute("gu", gu);
        return "good_detail";
    }
}
