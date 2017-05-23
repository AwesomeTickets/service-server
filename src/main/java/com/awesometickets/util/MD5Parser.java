package com.awesometickets.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Parser {

    private static MessageDigest md5 = null;
    private static BASE64Encoder base64en = null;

    public static String getMD5(String str) {
        if (md5 == null) {
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        if (base64en == null) {
            base64en = new BASE64Encoder();
        }


        //加密后的字符串
        String newstr= null;
        try {
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newstr;

    }
}
