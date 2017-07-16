package com.example.administrator.ccoupons.Tools;

/**
 * Created by CZJ on 2017/7/15.
 */

public class PasswordCheck {
    private String pass;
    private int strength = 0;
    public static final int ILLEGAL_CHAR = 0;
    public static final int TOO_SHORT = 1;
    public static final int TOO_SIMPLE = 2;
    public static final int PASS = 3;

    public void PasswordCheck(String str) {
        this.pass = str;
    }

    public int Check() {
        if (!(pass.length() > 7))
            return TOO_SHORT;
        if (pass.matches("^.*[^a-zA-Z0-9~!@#$%^&*()_\\-+=|\\<>,.?/:;'\\[\\]{}\"]+.*$"))
            return ILLEGAL_CHAR;
        if (pass.matches("^.*[a-z]+.*$"))
            strength++;
        if (pass.matches("^.*[A-Z]+.*$"))
            strength++;
        if (pass.matches("^.*[0-9]+.*$"))
            strength++;
        if (pass.matches("^.*[~!@#$%^&*()_\\-+=|\\<>,.?/:;'\\[\\]{}\"]+.*$"))
            strength++;
        if (strength < 2)
            return TOO_SIMPLE;
        return PASS;
    }
}
