package com.example.administrator.ccoupons.Fragments.Message;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Events.MessageRefreshEvent;
import com.example.administrator.ccoupons.Fragments.Message.Message;
import com.example.administrator.ccoupons.Fragments.Message.MessageClass;
import com.example.administrator.ccoupons.Fragments.Message.MessageDetailActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/7/16 0016.
 */

public class MessageFragment extends Fragment {


    @BindView(R.id.messageclass_recyclerview)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.message_ptr_frame)
    PtrFrameLayout messagePtrFrame;

    PtrFrameLayout currentRefreshLayout = null;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class MessageClassAdapter extends RecyclerView.Adapter<MessageClassAdapter.MessageViewHolder> {

        private Context mContext;
        private ArrayList<MessageClass> mMessageClassList;

        public class MessageViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.message_item_imageview)
            ImageView imageView;
            @BindView(R.id.message_item_title)
            TextView titleView;
            @BindView(R.id.message_item_subtitle)
            TextView subTitleView;
            @BindView(R.id.message_item_time)
            TextView timeTextView;

            CardView cardView;

            public MessageViewHolder(View view) {
                super(view);
                cardView = (CardView) view;
                ButterKnife.bind(this, view);
            }
        }


        public MessageClassAdapter(ArrayList<MessageClass> mList) {
            this.mMessageClassList = mList;
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            return new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.messageclass_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final MessageViewHolder holder, int position) {
            final MessageClass msgClass = mMessageClassList.get(position);
            holder.titleView.setText(msgClass.getClassName());
            holder.subTitleView.setText(msgClass.getSubtitle());
            holder.timeTextView.setText(msgClass.getTime());
            Glide.with(mContext).load(msgClass.getResId()).into(holder.imageView);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext.getApplicationContext(), MessageDetailActivity.class);
                    intent.putExtra("index", holder.getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) msgClass.getMessages());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
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
        unbinder = ButterKnife.bind(this, view);
        initTitles();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        adapter = new MessageClassAdapter(messageClasses);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(3));
        initPTR();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }


    private void initTitles() {
        messageClasses = new ArrayList<MessageClass>();

        for (int i = 0; i < DataHolder.MessageClasses.strings.length; i++) {
            MessageClass msgClass = new MessageClass(getResources().getString(DataHolder.MessageClasses.strings[i]));
            messageClasses.add(msgClass);
        }
    }


    private void initData() {

        ArrayList<Message> messageList = ((MyApp) getActivity().getApplicationContext()).getMessageList();
        if (messageList != null) {

            for (Message msg : messageList) {
                System.out.println("message from global list: " + msg.getCouponName());
                int catId = msg.getMessageCat();
                messageClasses.get(catId).add(msg);
            }
        }

    }


    private void initPTR() {
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        header.setPadding(0, PixelUtils.dp2px(getActivity(), 15), 0, 0);
        messagePtrFrame.setHeaderView(header);
        messagePtrFrame.addPtrUIHandler(header);
        messagePtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentRefreshLayout = frame;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentRefreshLayout.refreshComplete();
                    }
                }, 1600);
                //    categoryAppbar.setVisibility(View.INVISIBLE);
            //    new UniversalPresenter().getRecommendByRxRetrofit();
            }
        });
    }


    /**
     * @param event refresh message view
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(MessageRefreshEvent event) {
        initData();
    }

}
