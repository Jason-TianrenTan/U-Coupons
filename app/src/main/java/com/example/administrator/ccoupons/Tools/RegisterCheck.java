package com.example.administrator.ccoupons.Tools;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public class RegisterCheck {

    public int alertPhoneNumber(String phoneStr) {

        /*
        1. 13位数但是有非法字符
        2. 不足13位数暂不警告
        3. 出现非法字符直接警告
        4. 警告返回true
         */

        char[] pArray = phoneStr.toCharArray();
        int length = phoneStr.length();
        if (length < 11)
            return AlertType.LENGTH_ERROR;
        for (int i = 0; i < length; i++) {
            if (pArray[i] < '0' || pArray[i] > '9')
                return AlertType.ILLEGAL_CHAR;
        }

        return AlertType.NO_ERROR;
    }

    public int alertIdentifyCode(String codeStr) {
        char[] cArray = codeStr.toCharArray();
        if (codeStr.length() < 6)
            return AlertType.LENGTH_ERROR;
        for (int i = 0; i < 6; i++)
            if (cArray[i] < '0' || cArray[i] > '9')
                return AlertType.ILLEGAL_CHAR;
        return AlertType.NO_ERROR;
    }

    public int alertPassword(String pass) {
        int length = pass.length();
        if (length < 6 || length > 16) {
            return AlertType.LENGTH_ERROR;
        }
        if (!LegalInputChars(pass))
            return AlertType.ILLEGAL_CHAR;
        return AlertType.NO_ERROR;
    }
    public boolean LegalInputChars(String str) {
        //判断合法性
        return true;
    }


}
