package com.example.market1.Controller;

import com.example.market1.Model.Goods;
import com.example.market1.Service.GoodsService;
import com.example.market1.Utils.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;

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
}
