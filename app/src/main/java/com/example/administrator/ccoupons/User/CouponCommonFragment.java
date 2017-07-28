package com.example.administrator.ccoupons.User;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Fragments.MessageFragment;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/26 0026.
 */


public class CouponCommonFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<Coupon> couponList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.common_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.common_recyclerview);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        System.out.println("On create super fragment");

        couponList = (ArrayList<Coupon>) getArguments().getSerializable("coupons");
        if (couponList != null) {
            UserCouponInfoAdapter adapter = new UserCouponInfoAdapter(couponList);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }
        return view;
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
}
