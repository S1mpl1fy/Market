package com.example.market1.Controller;

import com.example.market1.Model.EntityType;
import com.example.market1.Model.HostHolder;
import com.example.market1.Service.GoodsService;
import com.example.market1.Service.LikeService;
import com.example.market1.Utils.MarketUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping("/market/like")
    @ResponseBody
    public String like(@Param("goodsId") int goodsId, HttpServletRequest request){
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_GOODS, goodsId);
        goodsService.updateLikeCount(goodsId, (int)likeCount);
        return MarketUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping("/market/dislike")
    @ResponseBody
    public String dislike(@Param("goodsId") int goodsId, HttpServletRequest request){
        long likeCount = likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_GOODS, goodsId);
        goodsService.updateLikeCount(goodsId, (int)likeCount);
        return MarketUtils.getJSONString(0,String.valueOf(likeCount));
    }
}
