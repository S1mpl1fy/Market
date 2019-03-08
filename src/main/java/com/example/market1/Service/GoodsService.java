package com.example.market1.Service;

import com.example.market1.DAO.GoodsDAO;
import com.example.market1.DAO.TicketDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.*;
import com.example.market1.Utils.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class GoodsService {
    @Autowired
    GoodsDAO goodsDAO;

    @Autowired
    TicketDAO ticketDAO;

    public String saveImage(MultipartFile file) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!MarketUtils.isFileAllowed(fileExt)) {
            return null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        Files.copy(file.getInputStream(), new File(MarketUtils.IMAGE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return MarketUtils.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

    public int addGoods(Goods goods){
        return goodsDAO.addGoods(goods);
    }

    public String publishGoods(PublishForm publishForm, HttpServletResponse response, HttpServletRequest request, Model model){
        String ticket = MarketUtils.getTicketFromRequst(request);
        int userId = 0;
        if(ticket == null) return "homepage";
        Ticket ticketLogin = ticketDAO.getTicket(ticket);
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
        goodsDAO.addGoods(goods);
        return "forward:index";
    }

    public int updateStatus(int goodsId, int likeCount){
        return goodsDAO.updateLikeCount(goodsId, likeCount);
    }

    public Goods getGoodsById(int id){
        return goodsDAO.getGoodsById(id);
    }

    public List<Goods> getLatestGoods(int start, int end){
        return goodsDAO.getLatestGoods(start, end);
    }
}
