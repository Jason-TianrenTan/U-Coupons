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

    public SearchHistoryManager(SharedPreferences p) {
        preferences = p;
        editor = preferences.edit();
    }

    public void addHistory(String str) {
        List<String> stringList = Arrays.asList(getHistoryList());
        if (stringList.size() == 20) {
            stringList.remove(0);
        }
        stringList.add(str);
        String history = "";
        for (int i = 0; i < stringList.size(); i++) {
            history += stringList.get(i) + ";";
        }
        editor.putString("search_history", history);
    }

    private String[] getHistoryList() {
        String history = preferences.getString("search_history", "");
        String[] list = history.split(";");
        return list;
    }

    public ArrayList<String> findHistory(String str) {
        ArrayList<String> result = new ArrayList<String>();
        String[] all = getHistoryList();
        for (int i = 0; i < all.length; i++) {
            if (all[i].matches(str)) {
                result.add(all[i]);
            }
        }
        Collections.reverse(result);
        return result;
    }
}
