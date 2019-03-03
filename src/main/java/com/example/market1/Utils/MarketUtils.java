package com.example.market1.Utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MarketUtils {

    public static String TOUTIAO_DOMAIN = "http://127.0.0.1:8080/";
    public static String IMAGE_DIR = "F:/upload/";
    public static String[] IMAGE_FILE_EXTD = new String[] {"png", "bmp", "jpg", "jpeg"};

    public static boolean isFileAllowed(String fileName) {
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    public static String getTicketFromRequst(HttpServletRequest request){
        String ticket  = null;
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        return ticket;
    }
}
