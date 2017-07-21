package com.example.administrator.ccoupons.Fragments;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.R;

/**
 * Created by CZJ on 2017/7/19.
 */

public class UserOptionClass {
    private String option;
    private int iconId = R.drawable.message_icon;

    public UserOptionClass(String option){
        this.option = option;
        //this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public String getOption() {
        return option;
    }
}
