package com.example.administrator.ccoupons.Search;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {


    private static final int HISTORY_MAX_RESULT = 10;//最大历史结果数
    private EditText searchText;
    private SearchHistoryFragment historyFragment;
    private PreSearchFragment preSearchFragment;
    private UserInfoManager userInfoManager;
    private String catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        catId = getIntent().getStringExtra("type");

        userInfoManager = new UserInfoManager(this);
        searchText = (EditText) findViewById(R.id.input_search);
        searchText.requestFocus();

        historyFragment = new SearchHistoryFragment();
        preSearchFragment = new PreSearchFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.search_activity_rootview);
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.search_activity_rootview:
                        hideSoftKeyBoard();
                        break;
                }
            }
        });

        TextView searchButton = (TextView) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = searchText.getText().toString();
                if (str != null) {
                    search(str);
                }
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = searchText.getText().toString();
                if (text.length() > 0) {
                    preSearch(text);
                    showFragment(preSearchFragment);
                }
                else
                    showFragment(historyFragment);
            }
        });

        //键盘上的搜索键
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    String str = searchText.getText().toString();
                    if (str != null) {
                        search(str);
                    }
                }
                return false;
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.search_framelayout, historyFragment);
        fragmentTransaction.add(R.id.search_framelayout, preSearchFragment);
        fragmentTransaction.commit();
        showFragment(historyFragment);
    }


    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(historyFragment);
        fragmentTransaction.hide(preSearchFragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void parseMessage(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray arr = obj.getJSONArray("result");
            ArrayList<String> preList = new ArrayList<>();
            for (int i=0;i<arr.length();i++) {
                JSONObject jsonObject = arr.getJSONObject(i);
                String result = jsonObject.getString("product");
                preList.add(result);
            }
            preSearchFragment.upDate(preList, catId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //{"result": [{"product": "what"}, {"product": "where"}, {"product": "why"}]}
    private void preSearch(String text) {
        String url = null;
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("keyword", text);
        url = GlobalConfig.base_URL + GlobalConfig.requestPreSearch_URL;
        if (catId != null && catId.length() > 0) {
            url = GlobalConfig.base_URL + GlobalConfig.requestCatPreSearch_URL;
            map.put("category", catId);
        }
        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {

            }

            @Override
            public void onConnectionFailed() {

            }
        });
        connectionManager.connect();
    }

    public void hideSoftKeyBoard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }



    private void search(String requestStr) {
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra("search_string", requestStr);
        intent.putExtra("categoryId", catId);
        userInfoManager.addHistory(requestStr);
        historyFragment.addHistory(requestStr);
        startActivity(intent);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideSoftKeyBoard();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }


}
