package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.SharedPreferences;

import java.util.ArrayList;
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
        String history = preferences.getString("search_history", "");
        history = history + str + ";";
        editor.putString("search_history", history);
    }

    public String[] getHistoryList(){
        String history = preferences.getString("search_history", "");
        String[] arrayList = history.split(";");
        return arrayList;
    }

    public ArrayList<String> findHistory(String str){
        ArrayList<String> result = new ArrayList<String>();
        String[] all = getHistoryList();
        for (int i = 0;i<all.length;i++){
            if (all[i].matches(str)){
                result.add(all[i]);
            }
        }
        Collections.reverse(result);
        return result;
    }
}
