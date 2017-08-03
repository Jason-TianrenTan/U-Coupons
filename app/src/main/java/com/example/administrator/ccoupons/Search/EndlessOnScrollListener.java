package com.example.administrator.ccoupons.Search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public abstract class EndlessOnScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLinearLayoutManager; //当前页，从0开始
    private int currentPage = 0;
    private int totalItemCount;
    private int previousTotal = 0;
    private int visibleItemCount;
    private int firstVisibleItem;
    private boolean loading = true;

    public EndlessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        System.out.println("visibleItem = " + visibleItemCount + ", total = " + totalItemCount +
                ", firtVisible = " + firstVisibleItem);
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);

}
