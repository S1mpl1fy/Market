package com.example.market1.Service;

import com.alibaba.fastjson.JSON;
import com.example.market1.DAO.*;
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
    UserDAO userDAO;

    @Autowired
    GoodsDAO goodsDAO;

    @Autowired
    TicketDAO ticketDAO;

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    MessageService messageService;

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

    public int updateLikeCount(int goodsId, int likeCount){
        return goodsDAO.updateLikeCount(goodsId, likeCount);
    }

    public List<Goods> getGoodsByUserId(int userId){
        return goodsDAO.getGoodsByUserId(userId);
    }

    public Goods getGoodsById(int id){
        return goodsDAO.getGoodsById(id);
    }

    public List<Goods> getLatestGoods(int start, int end){
        return goodsDAO.getLatestGoods(start, end);
    }

    public int deleteGoodsById(int goodsId){
        return goodsDAO.deleteGoodsById(goodsId);
    }

    public String dealGoods(int userId, int ownerId, int goodsId, int transactionId){

        String conversationId = (userId < ownerId ? String.format("%d_%d", userId, ownerId) : String.format("%d_%d", ownerId, userId));

        goodsDAO.updateStatus(goodsId, 2);
        String ext = String.valueOf(transactionDAO.getTransactionByGoods(goodsId).getId());
        return messageService.addMessage(10, ownerId, "用户" + userDAO.getNameById(userId) +
                "已经拍下了用户"+ userDAO.getNameById(ownerId) +"的商品: " + goodsDAO.getGoodsById(goodsId).getTitle(), conversationId, ext);
    }

    public String dealGoodsComplete(int transactionId){
        Transaction transaction  = transactionDAO.getTransaction(transactionId);
        String conversationId = (transaction.getBuyId() < transaction.getOwnerId() ? String.format("%d_%d", transaction.getBuyId(), transaction.getOwnerId())
                : String.format("%d_%d", transaction.getOwnerId(), transaction.getBuyId()));
        goodsDAO.updateStatus(transaction.getGoodsId(), 3);

        String ext = String.valueOf(transactionDAO.getTransactionByGoods(transactionId));
        return messageService.addMessage(10, transaction.getOwnerId(), "用户" + userDAO.getNameById(transaction.getBuyId()) +
                "与"+ userDAO.getNameById(transaction.getOwnerId()) +"关于商品: " + goodsDAO.getGoodsById(transaction.getGoodsId()).getTitle() +
                " 的交易已经完成！", conversationId, ext);
    }

    public String cancelGoodsDeal(int transactionId){
        Transaction transaction  = transactionDAO.getTransaction(transactionId);
        String conversationId = (transaction.getBuyId() < transaction.getOwnerId() ? String.format("%d_%d", transaction.getBuyId(), transaction.getOwnerId())
                : String.format("%d_%d", transaction.getOwnerId(), transaction.getBuyId()));
        goodsDAO.updateStatus(transaction.getGoodsId(), 0);

        String ext = String.valueOf(transactionDAO.getTransactionByGoods(transactionId));
        return messageService.addMessage(10, transaction.getOwnerId(), "用户" + userDAO.getNameById(transaction.getBuyId()) +
                "与"+ userDAO.getNameById(transaction.getOwnerId()) +"关于商品: " + goodsDAO.getGoodsById(transaction.getGoodsId()).getTitle() +
                " 的交易已经取消！", conversationId, ext);
    }

    public int updateGoodsStatus(int goodsId, int status){
        return goodsDAO.updateStatus(goodsId, status);
    }
}
