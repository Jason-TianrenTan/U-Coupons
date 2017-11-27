package com.example.administrator.ccoupons.Tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by CZJ on 2017/7/16.
 */

public class PasswordEncoder {


    /**
     *
     * @param str String to be encoded
     * @return String after encoding
     * @throws NoSuchAlgorithmException
     */
    public static String EncodeByMd5(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        str += "UHui";
        md5.update(str.getBytes());
        byte[] m = md5.digest(); //加密
        return toHex(m);
    }


    /**
     * Change decimal into hex
     * @param hash
     * @return password in hex-encode
     */
    public static final String toHex(byte hash[]) {
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) hash[i] & 0xff, 16));
        }
        return buf.toString();
    }
}
