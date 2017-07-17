package com.example.administrator.ccoupons.Tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/7/16 0016.
 */

public class StringEncoder {
    MessageDigest md5;
    String string;
    char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    public StringEncoder(String str) {
        string = str;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String getMD5() {
        byte[] btInput = string.getBytes();
        md5.update(btInput);
        byte[] md = md5.digest();
        int len = md.length;
        char[] str = new char[len*2];
        int k = 0;
        for (int i = 0; i < len; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
