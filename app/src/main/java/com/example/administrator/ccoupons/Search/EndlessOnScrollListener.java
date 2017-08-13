package com.example.administrator.ccoupons.Search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public abstract class EndlessOnScrollListener extends RecyclerView.OnScrollListener {


    public static final int VISIBLE_THRESHOLD = 2;
    private LinearLayoutManager mLinearLayoutManager;
    private int totalItemCount;
    private int lastVisibleItem;
    private int previousTotal = 0;
    private boolean loading = true;
    private boolean first = true;

    public EndlessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItemCount = mLinearLayoutManager.getItemCount();
        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        System.out.println("scrolling, total = " + totalItemCount + ", last visible = " + lastVisibleItem);
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && totalItemCount < (lastVisibleItem + VISIBLE_THRESHOLD)) {
            System.out.println("Total = " + totalItemCount + ", last visible = " + lastVisibleItem + ",loading more...");
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();

}
