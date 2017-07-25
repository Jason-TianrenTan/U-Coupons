package com.example.administrator.ccoupons.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Category;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.SearchActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/16 0016.
 */

public class MessageFragment extends Fragment {



    public class MessageClassAdapter extends RecyclerView.Adapter<MessageClassAdapter.MessageViewHolder> {

        private Context mContext;
        private ArrayList<MessageClass> mMessageClassList;

        public class MessageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleView, subTitleView, timeTextView;
            CardView cardView;
            public MessageViewHolder(View view) {
                super(view);
                cardView = (CardView)view;
                imageView = (ImageView) view.findViewById(R.id.message_item_imageview);
                titleView = (TextView) view.findViewById(R.id.message_item_title);
                subTitleView = (TextView) view.findViewById(R.id.message_item_subtitle);
                timeTextView = (TextView) view.findViewById(R.id.message_item_time);
            }
        }


        public MessageClassAdapter(ArrayList<MessageClass> mList) {
            this.mMessageClassList = mList;
        }

        @Override
        public MessageClassAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.messageclass_item, parent, false);
            final MessageViewHolder holder = new MessageViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            MessageClass msgClass = mMessageClassList.get(position);
            holder.titleView.setText(msgClass.getClassName());
            holder.subTitleView.setText(msgClass.getSubtitle());
            holder.timeTextView.setText(msgClass.getTime());
            Glide.with(mContext).load(msgClass.getResId()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mMessageClassList.size();
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                if (parent.getChildLayoutPosition(view) == 3)
                    outRect.top = 100;
                else
                    outRect.top = 0;
            }
        }
    }
    ArrayList<MessageClass> messageClasses;
    MessageClassAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_message, container, false);
        initData();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.messageclass_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageClassAdapter(messageClasses);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(3));
        return view;
    }


    private void initData() {
        messageClasses = new ArrayList<MessageClass>();
        for (int i = 0; i < DataHolder.MessageClasses.strings.length; i++) {

            ArrayList<Message> msgList = new ArrayList<>();
            MessageClass msgClass = new MessageClass(getResources().getString(DataHolder.MessageClasses.strings[i]), msgList);
            messageClasses.add(msgClass);
        }
    }

}
