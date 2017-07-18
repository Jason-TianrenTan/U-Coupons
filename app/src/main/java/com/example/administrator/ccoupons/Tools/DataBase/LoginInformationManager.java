package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.SharedPreferences;

/**
 * Created by CZJ on 2017/7/16.
 */

public class LoginInformationManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public LoginInformationManager(SharedPreferences p) {
        preferences = p;
        editor = preferences.edit();
    }

    public boolean getAutoLogin() {
        return preferences.getBoolean("auto_login", false);
    }

    public String getUserName() {
        return preferences.getString("user_name", "");
    }

    public String getPassword() {
        return preferences.getString("password", "");
    }

    public LoginInformationManager setAutoLogin(boolean b) {
        editor.putBoolean("auto_login", b).commit();
        return this;
    }

    public LoginInformationManager setUserNmae(String str) {
        editor.putString("user_name", str).commit();
        return this;
    }

    public LoginInformationManager setPassword(String str) {
        editor.putString("password", str).commit();
        return this;
    }

    public LoginInformationManager clear() {
        editor.clear().commit();
        return this;
    }

    public LoginInformationManager removeUserName(){
        editor.remove("user_name").commit();
        return this;
    }

    public LoginInformationManager removePassword(){
        editor.remove("password").commit();
        return this;
    }
}
