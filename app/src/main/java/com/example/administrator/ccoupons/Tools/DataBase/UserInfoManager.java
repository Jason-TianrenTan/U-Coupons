package com.example.administrator.ccoupons.Tools.DataBase;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

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
    private int MAX_SIZE = 10;
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
        for (String h : history) {
            System.out.println(h);
        }
        return history;
    }

    public void addHistory(String str) {
        if (history.size() == MAX_SIZE)
            deleteHistory(MAX_SIZE);
        history.add(0, str);
        changeHistoryData();
    }

    public void deleteHistory(int i) {
        history.remove(i - 1);
        changeHistoryData();
    }

    public void clearHistory() {
        editor.clear().commit();
    }

    private void changeHistoryData() {
        String historyData = "";
        for (String str : history) {
            historyData = historyData + str + ";";
        }
        editor.putString("search_history", historyData).commit();
    }
}