package com.example.administrator.ccoupons.Tools;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public class AlertType {
    public static final int NO_ERROR = 0;//合法
    public static final int ILLEGAL_CHAR = 1;//非法字符
    public static final int LENGTH_ERROR = 2;//长度不对
    public static final int TOO_SIMPLE = 3;//过于简单
    public static final String ILLEGAL_CHAR_STR = "不能含有非法字符";
    public static final String LENGTH_ERROR_PHONE_STR = "长度必须为11位";
    public static final String LENGTH_ERROR_CODE_STR = "长度必须为4位";
    public static final String LENGTH_ERROR_PASSWORD_STR = "长度必须为6~16位";
    public static final String TOO_SIMPLE_STR = "密码强度太弱";
}
