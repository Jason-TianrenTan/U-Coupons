package com.example.administrator.ccoupons.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {


    private static final int HISTORY_MAX_RESULT = 10;//最大历史结果数

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
        mRecyclerView.setNestedScrollingEnabled(false);

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.search_nestedscrollview);
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

        scrollView.setSmoothScrollingEnabled(true);


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
        //public ImageView imageView;
        public LinearLayout historyView;
        public ImageView imageView;

        public HistoryViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.history_text);
            historyView = (LinearLayout) view.findViewById(R.id.history_view);
            imageView = (ImageView) view.findViewById(R.id.history_delete);
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
                    adapter.notifyItemRangeRemoved(0, mHistoryList.size());
                    userInfoManager.clearHistory();
                    adapter.notifyItemRangeChanged(0, mHistoryList.size());
                    Toast.makeText(SearchActivity.this, "删除", Toast.LENGTH_SHORT).show();
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
            if (position == (mHistoryList.size()))
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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (position != (mHistoryList.size())) {
                final String historyString = mHistoryList.get(position);
                System.out.println("String = " + historyString + ", pos = " + position);
                HistoryViewHolder viewHolder = (HistoryViewHolder) holder;
                viewHolder.textView.setText(historyString);
                //   holder.imageView.
                viewHolder.imageView.setImageResource(R.drawable.clear_history_icon);
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.notifyItemRemoved(position);
                        userInfoManager.deleteHistory(position + 1);
                        adapter.notifyItemRangeChanged(0, mHistoryList.size());
                    }
                });
                viewHolder.historyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchText.setText(historyString);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mHistoryList.size() + 1;
        }

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
