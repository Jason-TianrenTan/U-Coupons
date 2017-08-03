package com.example.administrator.ccoupons.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Purchase.CouponDetailActivity;
import com.example.administrator.ccoupons.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String[] statList = "已出售 即将过期 已过期 即将过期 即将过期 系统消息".split(" ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        bindViews();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.message_detail_recyclerview);

        MessageDetailAdapter adapter = new MessageDetailAdapter((ArrayList<Message>) getIntent().getSerializableExtra("list"));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void bindViews() {
        int index = getIntent().getIntExtra("index", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.message_fragment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView title = (TextView) findViewById(R.id.message_detail_title);
        title.setText(DataHolder.MessageClasses.strings[index]);
    }


    public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.MessageDetailViewHolder> {

        private Context mContext;
        private ArrayList<Message> mMessageList;

        public class MessageDetailViewHolder extends RecyclerView.ViewHolder {

            CardView rootView;
            TextView statText;
            TextView nameText;
            TextView priceText;
            TextView checkText;

            public MessageDetailViewHolder(View view) {
                super(view);
                rootView = (CardView) view;
                statText = (TextView) view.findViewById(R.id.message_detail_stat_text);
                nameText = (TextView) view.findViewById(R.id.message_detail_name_text);
                priceText = (TextView) view.findViewById(R.id.message_detail_price_text);
                checkText = (TextView) view.findViewById(R.id.message_detail_check_text);
            }
        }


        public MessageDetailAdapter(ArrayList<Message> cList) {
            this.mMessageList = cList;
        }

        @Override
        public MessageDetailAdapter.MessageDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_detail_item, parent, false);
            MessageDetailAdapter.MessageDetailViewHolder holder = new MessageDetailAdapter.MessageDetailViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MessageDetailAdapter.MessageDetailViewHolder holder, int position) {
            final Message message = mMessageList.get(position);
            holder.nameText.setText(message.getCouponName());
            holder.statText.setText(statList[message.getMessageCat()]);

            final int msgCat = message.getMessageCat();
            if (msgCat == 1 || msgCat == 3 || msgCat == 4) {
                holder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestCouponDetail(message);
                    }
                });

                holder.checkText.setTextColor(Color.parseColor("#f0f0f0"));
            }
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }
    }


    private void requestCouponDetail(Message msg) {
        Intent intent = new Intent(MessageDetailActivity.this, CouponDetailActivity.class);
        intent.putExtra("type", "message");
        intent.putExtra("msgCat", msg.getMessageCat() + "");
        intent.putExtra("Coupon", msg.getCoupon());
        intent.putExtra("msgId", msg.getMessageId());
        startActivity(intent);
    }
}
