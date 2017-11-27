package com.example.administrator.ccoupons.Tools.DataBase;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CZJ on 2017/7/16.
 */

public class LoginInformationManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    /**
     * get the SharedPreferences manager instance
     * @param activity
     */
    public LoginInformationManager(Activity activity) {
        preferences = activity.getSharedPreferences("UserInfomation", MODE_PRIVATE);
        editor = preferences.edit();
    }


    /**
     * get the auto-login state
     * @return auto-login state
     */
    public boolean getAutoLogin() {
        return preferences.getBoolean("auto_login", false);
    }


    /**
     * get the username
     * @return username
     */
    public String getUsername() {
        return preferences.getString("username", "");
    }


    /**
     * get the saved password
     * @return password
     */
    public String getPassword() {
        return preferences.getString("password", "");
    }

<<<<<<< HEAD
    public String getPortraitPath() {
        return preferences.getString("portrait_path", "");
    }

=======

    /**
     * set the auto-login state
     * @return
     */
>>>>>>> Czj
    public LoginInformationManager setAutoLogin(boolean b) {
        editor.putBoolean("auto_login", b).commit();
        return this;
    }


    /**
     * set the username
     * @return
     */
    public LoginInformationManager setUsername(String str) {
        editor.putString("username", str).commit();

        return this;
    }


    /**
     * set the saved password
     * @return
     */
    public LoginInformationManager setPassword(String str) {
        editor.putString("password", str).commit();
        return this;
    }

<<<<<<< HEAD
    public LoginInformationManager setPortraitPath(String str) {
        editor.putString("portrait_path", str).commit();
        return this;
    }

=======

    /**
     * clear the record
     * @return
     */
>>>>>>> Czj
    public LoginInformationManager clear() {
        editor.clear().commit();
        return this;
    }


    /**
     * remove the saved username
     * @return
     */
    public LoginInformationManager removeUsername(){

        editor.remove("username").commit();
        return this;
    }


    /**
     * remove the saved password
     * @return
     */
    public LoginInformationManager removePassword(){
        editor.remove("password").commit();
        return this;
    }
}
