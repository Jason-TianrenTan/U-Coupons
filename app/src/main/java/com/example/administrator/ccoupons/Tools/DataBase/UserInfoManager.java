package com.example.administrator.ccoupons.Tools.DataBase;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CZJ on 2017/7/25.
 */

public class UserInfoManager {
    private String username;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ArrayList<String> history;

    public UserInfoManager(Activity activity) {
        username = (new LoginInformationManager(activity)).getUsername();
        preferences = activity.getSharedPreferences(username, MODE_PRIVATE);
        editor = preferences.edit();
        history = new ArrayList(Arrays.asList(
                preferences.getString("search_history", "")
                        .split(";")));
    }

    public ArrayList<String> getHistoryList() {
        return history;
    }

    public void addHistory(String str) {
        history.add(0, str);
        changeHistoryData();
    }

    public void deleteHistory(int i) {
        history.remove(i);
        changeHistoryData();
    }

    public void clearHistory() {
        editor.clear().commit();
    }

    private void changeHistoryData() {
        String historyData = "";
        for (String str : history) {
            historyData += str;
        }
        editor.putString("search_history", historyData).commit();
    }

    public String getPortraitUrl() {
        return preferences.getString("portrait_path", "");
    }

    public void setPortraitUrl(String str) {
        editor.putString("portrait_path", str).commit();
    }

    public void setUserInfo(String nickname, boolean sex, int age, int UB) {
        editor.putString("nickname", nickname);
        editor.putBoolean("sex", sex);
        editor.putInt("age", age);
        editor.putInt("UB", UB);
        editor.commit();
    }

    public String getNickname() {
        return preferences.getString("nickname", "");
    }

    public boolean getSex() {
        return preferences.getBoolean("sex", true);
    }

    public int getAge() {
        return preferences.getInt("age", 0);
    }

    public int getUB() {
        return preferences.getInt("UB", 0);
    }
}