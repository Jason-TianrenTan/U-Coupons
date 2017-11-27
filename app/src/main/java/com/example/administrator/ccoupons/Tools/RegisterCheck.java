package com.example.administrator.ccoupons.Tools;


/**
 * Created by Administrator on 2017/7/15 0015.
 */

public class RegisterCheck {


    /**
     1.13位数但是有非法字符
     2. 不足13位数暂不警告
     3. 出现非法字符直接警告
     4. 警告返回true
     * @param phoneStr
     * @return
     */
    public int alertPhoneNumber(String phoneStr) {
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
        if (codeStr.length() < 4)
            return AlertType.LENGTH_ERROR;
        for (int i = 0; i < 4; i++)
            if (cArray[i] < '0' || cArray[i] > '9')
                return AlertType.ILLEGAL_CHAR;
        return AlertType.NO_ERROR;
    }

    public int alertPassword(String pass) {
        int length = pass.length();
        if (length < 6 || length > 16) {
            return AlertType.LENGTH_ERROR;
        }
        if (pass.matches("^.*[^a-zA-Z0-9~!@#$%^&*()_\\-+=|\\<>,.?/:;'\\[\\]{}\"]+.*$"))
            return AlertType.ILLEGAL_CHAR;
        if (passwordStrength(pass))
            return AlertType.TOO_SIMPLE;
        return AlertType.NO_ERROR;
    }


    /**
     * Check strength of password
     * @param pass
     * @return
     */
    private boolean passwordStrength(String pass) {
        //判断密码是否过于简单
        int strength = 0;

        if (pass.matches("^.*[a-z]+.*$"))
            strength++;
        if (pass.matches("^.*[A-Z]+.*$"))
            strength++;
        if (pass.matches("^.*[0-9]+.*$"))
            strength++;
        if (pass.matches("^.*[~!@#$%^&*()_\\-+=|\\<>,.?/:;'\\[\\]{}\"]+.*$"))
            strength++;

        if (strength < 2)
            return true;
        return false;
    }
}
