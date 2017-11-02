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


    /**
     * get the UserInfoManager instance
     * @param activity
     */
    public UserInfoManager(Activity activity) {
        username = (new LoginInformationManager(activity)).getUsername();
        preferences = activity.getSharedPreferences(username, MODE_PRIVATE);
        editor = preferences.edit();
        String historyData = preferences.getString("search_history", "");
        history = new ArrayList(Arrays.asList(historyData.split(";")));
        if (history.get(0).equals("")) {
            history.remove(0);
        }
    }


    /**
     * get the history list
     * @return
     */
    public ArrayList<String> getHistoryList() {
        return history;
    }


    /**
     * add a history record
     * @param str
     */
    public void addHistory(String str) {
        while (history.size() == MAX_SIZE)
            deleteHistory(MAX_SIZE);
        if (history.contains(str)) {
            history.remove(str);
        }
        history.add(0, str);
        changeHistoryData();
    }


    /**
     * delete the first history
     * @param i
     */
    public void deleteHistory(int i) {
        history.remove(i - 1);
        changeHistoryData();
    }


    /**
     * clear all history
     */
    public void clearHistory() {
        history.clear();
        editor.clear().commit();
    }


    /**
     * update the history data
     */
    private void changeHistoryData() {
        String historyData = "";
        for (String str : history) {
            historyData = historyData + str + ";";
        }
        editor.putString("search_history", historyData).commit();
    }


    /**
     * set the history list
     * @param history
     */
    public void setHistory(ArrayList<String> history) {
        this.history = history;
        changeHistoryData();
    }
}