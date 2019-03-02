package com.example.market1.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MyTools {
    public static String getMD5(String string) throws Exception{
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        }catch (Exception e){
            throw new Exception("getMD5 failed: " + e.getMessage());
        }
    }

    public static void log(String string){
        System.out.println("+-+-+-+-+-:" + string);
    }
}
