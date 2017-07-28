package com.example.administrator.ccoupons.Search;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StringLoader;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.CategoryAdapter;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.SystemBarTintManager;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final int VERTICAL_ITEM_SPACE = 48;

    private static final int HISTORY_MAX_RESULT = 10;//最大历史结果数


    private boolean firstTime = true;
    private boolean shouldHide = false;
    private RecyclerView mRecyclerView;
    private HistoryAdapter adapter;
    private ArrayList<String> mHistoryList;
    private UserInfoManager userInfoManager;


    private EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = (EditText) findViewById(R.id.input_search);
        searchText.requestFocus();

        userInfoManager = new UserInfoManager(this);

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

        initHistoryData();
        setRecyclerView();
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

        TextView searchButton = (TextView)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = searchText.getText().toString();
                if (str != null) {
                    search(str);
                }
            }
        });


    }


    public void hideSoftKeyBoard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }

    //history
    private void initHistoryData() {
        mHistoryList = userInfoManager.getHistoryList();
        //mHistoryList = new ArrayList<>();
        //requestHistoryData();
    }
/*
    private void requestHistoryData() {
        String result = null;
        int pos = 0;
        while (!(result = DataHolder.History.requestData(pos)).equals("EOF")) {
            mHistoryList.add(result);
            pos++;
            if (pos == HISTORY_MAX_RESULT)
                break;
        }
        mHistoryList.add(getResources().getString(R.string.HISTORY_EOF));
    }
    */

    //设置RecyclerView
    private void setRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.history_recyclerview);
        adapter = new HistoryAdapter(mHistoryList);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shouldHide = false;
            }
        });
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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (shouldHide) {
                    if (firstTime)
                        firstTime = false;
                    else
                        hideSoftKeyBoard();
                }
                if (!shouldHide)
                    shouldHide = true;
            }
        });

    }

    private void search(String requestStr) {
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra("search_string", requestStr);
        //mHistoryList.remove(mHistoryList.size() - 2);
        //mHistoryList.add(0, requestStr);
        userInfoManager.addHistory(requestStr);
        //还需要更新缓存，添加内容
        adapter.notifyDataSetChanged();
        startActivity(intent);
    }


    //历史记录ViewHolder
    private class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public HistoryViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.history_text);
            imageView = (ImageView) view.findViewById(R.id.history_icon);
        }
    }

    //底部清除历史记录ViewHolder
    private class ClearHistoryViewHolder extends RecyclerView.ViewHolder {

        public ClearHistoryViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO:clear history
                    //清除历史记录
                }
            });
        }
    }


    private class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int HISTORY_TYPE = 1;
        private static final int CLEAR_TYPE = 0;

        private Context mContext;
        private ArrayList<String> mHistoryList;

        @Override
        public int getItemViewType(int position) {
            if (position == (mHistoryList.size() - 1))
                return CLEAR_TYPE;
            return HISTORY_TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view;
            if (viewType == HISTORY_TYPE) {
                view = LayoutInflater.from(mContext).inflate(R.layout.history_item, parent, false);
                return new HistoryViewHolder(view);
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.clear_history_view, parent, false);
                return new ClearHistoryViewHolder(view);
            }

        }

        public HistoryAdapter(ArrayList<String> hList) {
            this.mHistoryList = hList;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            String historyString = mHistoryList.get(position);
            System.out.println("String = " + historyString + ", pos = " + position);
            if (position != (mHistoryList.size() - 1)) {
                HistoryViewHolder viewHolder = (HistoryViewHolder) holder;
                viewHolder.textView.setText(historyString);
                //   holder.imageView.
                viewHolder.imageView.setImageResource(R.drawable.search_icon_big);
            }
        }

        @Override
        public int getItemCount() {
            return mHistoryList.size();
        }

    }


    //设置系统
    private void setSystemBar(int colorId) {
        //System Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //系统版本大于19
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorId);//设置标题栏颜色，此颜色在color中声明

        //字体
        Class clazz = this.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (true) {
                extraFlagField.invoke(this.getWindow(), darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(this.getWindow(), 0, darkModeFlag);//清除黑色字体
            }
        } catch (Exception e) {

        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;        // a|=b的意思就是把a和b按位或然后赋值给a   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
        } else {
            winParams.flags &= ~bits;        //&是位运算里面，与运算  a&=b相当于 a = a&b  ~非运算符
        }
        win.setAttributes(winParams);
    }


}
