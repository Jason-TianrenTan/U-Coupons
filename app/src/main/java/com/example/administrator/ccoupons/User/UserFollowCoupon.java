package com.example.administrator.ccoupons.User;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;

import java.util.ArrayList;

public class UserFollowCoupon extends SlideBackActivity {
    private UserCouponInfoAdapter adapter;
    private Coupon coupon = new Coupon();
    ArrayList<Coupon> arrayList = new ArrayList<Coupon>();
    private RecyclerView recyclerView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_follow_coupon);
        initView();
        setToolBar();
        setRecyclerView();
        isEmpty();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.ufol_recyclerview);
        linearLayout = (LinearLayout) findViewById(R.id.ufol_bg);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.ufol_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        //TODO:获取卖出优惠券列表
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
                outRect.top = 20;
            }
        }
    }

    public void setRecyclerView() {
        arrayList.add(coupon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserCouponInfoAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new UserFollowCoupon.SpacesItemDecoration(3));
    }

    public void isEmpty() {
        if (arrayList.size() == 0) {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }
}
