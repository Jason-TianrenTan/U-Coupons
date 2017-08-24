package com.example.administrator.ccoupons.User;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.CouponListEvent;
import com.example.administrator.ccoupons.Fragments.MessageFragment;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.EndlessOnScrollListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/26 0026.
 */


public class CouponCommonFragment extends Fragment {


    private String[] typeStrings = "UserUsed,UserUnsold,UserOnsale".split(",");
    public static final int USED_TYPE = 0,
            UNSOLD_TYPE = 1,
            ONSALE_TYPE = 2;
    public static final int COUPON_MAX_RESULT = 4;
    @BindView(R.id.common_recyclerview)
    public RecyclerView recyclerView;

    protected ArrayList<Coupon> fullList,
            adapterList;
    protected UserCouponInfoAdapter adapter;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.common_fragment, container, false);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        adapterList = new ArrayList<>();
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        initData();

        return view;
    }


    public void initData() {

    }

    protected void requestResults(int start) {
        int count = 0;
        for (int i = start; i < fullList.size(); i++, count++) {
            if (count == COUPON_MAX_RESULT)
                break;
            adapterList.add(fullList.get(i));
            System.out.println("added one");
        }
        if (adapterList.size() < fullList.size())
            adapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.load_footer, recyclerView, false));
        else adapter.setFooterView(null);
        adapter.notifyDataSetChanged();

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    protected void setData(ArrayList<Coupon> cList) {
        this.fullList = cList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new EndlessOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                requestResults(adapterList.size());
            }
        });
    }
}
