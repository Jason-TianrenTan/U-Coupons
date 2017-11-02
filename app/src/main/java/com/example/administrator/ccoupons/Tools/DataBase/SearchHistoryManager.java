package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by CZJ on 2017/7/17.
 */

public class SearchHistoryManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    /**
     * init SearchHistoryManager
     * @param p
     */
    public SearchHistoryManager(SharedPreferences p) {
        preferences = p;
        editor = preferences.edit();
    }


    /**
     * add a history record
     * @param str
     */
    public void addHistory(String str) {
        List<String> stringList = Arrays.asList(getHistoryList());
        if (stringList.size() == 10) {
            stringList.remove(0);
        }
        stringList.add(str);
        String history = "";
        for (int i = 0; i < stringList.size(); i++) {
            history += stringList.get(i) + ";";
        }
        editor.putString("search_history", history);
    }


    /**
     * get the record list
     */
    private String[] getHistoryList() {
        String history = preferences.getString("search_history", "");
        String[] list = history.split(";");
        return list;
    }
}
