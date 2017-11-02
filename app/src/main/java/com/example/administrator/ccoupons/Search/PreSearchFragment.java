package com.example.administrator.ccoupons.Search;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class PreSearchFragment extends Fragment {

    private UserInfoManager userInfoManager;
    private RecyclerView recyclerView;
    private ArrayList<String> preList;
    private PreSearchAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.presearch_fragment, container, false);

        initData();
        userInfoManager = new UserInfoManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.presearch_recyclerview);

        adapter = new PreSearchAdapter(preList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        android.support.v7.widget.DividerItemDecoration dividerItemDecoration = new android.support.v7.widget.DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.category_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }


    /**
     * update view list
     * @param arrayList
     * @param catId category id
     */
    public void upDate(ArrayList<String> arrayList, String catId) {
        this.preList = arrayList;
        adapter = new PreSearchAdapter(preList);
        adapter.catId = catId;
        recyclerView.setAdapter(adapter);
    }


    /**
     * initialize arraylist
     */
    private void initData() {
        preList = new ArrayList<>();
    }


    /**
     * adapter for presearch
     */
    public class PreSearchAdapter extends RecyclerView.Adapter<PreSearchAdapter.PreSearchViewHolder> {

        private Context mContext;
        private ArrayList<String> resultList;
        private String catId;

        public class PreSearchViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            LinearLayout rootView;
            public PreSearchViewHolder(View view) {
                super(view);
                rootView = (LinearLayout)view;
                textView = view.findViewById(R.id.presearch_text);
            }
        }


        public PreSearchAdapter(ArrayList<String> cList) {
            this.resultList = cList;
        }

        @Override
        public PreSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.presearch_item, parent, false);
            final PreSearchViewHolder holder = new PreSearchViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(PreSearchViewHolder holder, final int position) {
            String result = resultList.get(position);
            holder.textView.setText(result);
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String result = resultList.get(position);
                    Intent intent = new Intent(mContext, SearchResultActivity.class);
                    intent.putExtra("search_string", result);
                    if (catId != null)
                        intent.putExtra("categoryId", catId);
                    userInfoManager.addHistory(result);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return resultList.size();
        }
    }
}
