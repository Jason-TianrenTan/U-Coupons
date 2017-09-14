package com.example.administrator.ccoupons.Search;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class SearchHistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HistoryAdapter adapter;
    private ArrayList<String> mHistoryList;
    private UserInfoManager userInfoManager;
    private String catId = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_history_fragment, container, false);
        initHistoryData();
        setRecyclerView(view);
        return view;
    }

    //history
    private void initHistoryData() {
        userInfoManager =new UserInfoManager(getActivity());
        mHistoryList = userInfoManager.getHistoryList();
    }
    //设置RecyclerView
    private void setRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.history_recyclerview);
        adapter = new HistoryAdapter(mHistoryList);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        NestedScrollView scrollView = (NestedScrollView) view.findViewById(R.id.search_nestedscrollview);
        scrollView.setSmoothScrollingEnabled(true);

    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
    //确定清空记录对话框
    private void showClearDialog() {
        final AlertDialog.Builder clearDialog =
                new AlertDialog.Builder(getActivity());
        clearDialog.setMessage("确定要清空所有搜索历史?");
        clearDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清空登录信息
                        adapter.notifyItemRangeRemoved(0, mHistoryList.size());
                        userInfoManager.clearHistory();
                        adapter.notifyItemRangeChanged(0, mHistoryList.size());
                        Toast.makeText(getActivity().getApplicationContext(), "历史记录已清除", Toast.LENGTH_SHORT).show();
                    }
                });
        clearDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        clearDialog.show();
    }

    public void addHistory(String str) {
        userInfoManager.addHistory(str);
        adapter.notifyDataSetChanged();
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
                        //TODO: 启动，用historyString
                        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                        intent.putExtra("search_string", historyString);
                        if (catId != null)
                            intent.putExtra("categoryId", catId);
                        startActivity(intent);
                        addHistory(historyString);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mHistoryList.size() + 1;
        }

    }


    //底部清除历史记录ViewHolder
    private class ClearHistoryViewHolder extends RecyclerView.ViewHolder {

        public ClearHistoryViewHolder(View view) {
            super(view);
            LinearLayout clear = (LinearLayout) view.findViewById(R.id.clear_view);
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO:clear history
                    //清除历史记录
                    showClearDialog();
                }
            });
        }
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

}
