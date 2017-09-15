package com.example.administrator.ccoupons.Search;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.User.CouponCommonFragment;
import com.example.administrator.ccoupons.User.UserCoupons.CouponModifiedEvent;
import com.example.administrator.ccoupons.User.UserCoupons.User.UserUsedAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class SearchResultFragment extends SearchCommonFragment {


    private String keyWord = null, order = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * @param clistEvent search result list
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponListEvent clistEvent) {
        if (clistEvent.getListname().equals("UserSearch")) {
            setData(clistEvent.getList());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponModifiedEvent event) {

    }


    /**
     * init data
     */
    @Override
    public void initData() {
        if (keyWord == null)
            keyWord = getArguments().getString("keyWord");
        clear();
        System.out.println("at init data in normal search, key word = " + keyWord + ", order = " + order);
        adapterList = new ArrayList<>();
        adapter = new ResultAdapter(adapterList);
        new UniversalPresenter().getSearchResultByRxRetrofit(keyWord, order);
    }


    /**
     * clear list
     */
    private void clear() {
        if (fullList != null) {
            int size = fullList.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    fullList.remove(0);
                }
                adapter.notifyItemRangeRemoved(0, size);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
