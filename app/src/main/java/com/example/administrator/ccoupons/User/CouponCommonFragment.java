package com.example.administrator.ccoupons.User;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.EndlessOnScrollListener;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.User.UserCoupons.CouponModifiedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/7/26 0026.
 */


public abstract class CouponCommonFragment extends Fragment {


    public static final int USED_TYPE = 0,
            UNSOLD_TYPE = 1,
            ONSALE_TYPE = 2;
    public static final int COUPON_MAX_RESULT = 4;
    @BindView(R.id.common_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    public RelativeLayout emptyView;
    @BindView(R.id.common_ptr_frame)
    PtrFrameLayout commonPtrFrame;

    protected ArrayList<Coupon> fullList,
            adapterList;
    protected UserCouponInfoAdapter adapter;
    protected Context mContext;
    private Unbinder unbinder;
    private PtrFrameLayout currentRefreshLayout = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.common_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        emptyView.setVisibility(View.INVISIBLE);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        initPTR();
        initData();

        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponModifiedEvent event) {
        System.out.println("modified event");
    }


    private void initPTR() {
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        header.setPadding(0, PixelUtils.dp2px(getActivity(), 15), 0, 0);
        commonPtrFrame.setHeaderView(header);
        commonPtrFrame.addPtrUIHandler(header);
        commonPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentRefreshLayout = frame;
                //initData();
                new UniversalPresenter().getUserUsedByRxRetrofit(((MyApp)getActivity().getApplicationContext()).getUserId());
                new UniversalPresenter().getUserOnsaleByRxRetrofit(((MyApp)getActivity().getApplicationContext()).getUserId());
                new UniversalPresenter().getUserUnsoldByRxRetrofit(((MyApp)getActivity().getApplicationContext()).getUserId());

            }
        });
    }


    public abstract void initData();

    /**
     * @param start   beginning index
     * @param ceiling max requests
     */
    protected void requestResults(int start, int ceiling) {
        int count = 0;
        for (int i = start; i < fullList.size(); i++, count++) {
            if (count == ceiling)
                break;
            adapterList.add(fullList.get(i));
        }
        if (adapterList.size() < fullList.size())
            adapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.load_footer, recyclerView, false));
        else adapter.setFooterView(null);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                requestResults(adapterList.size(), COUPON_MAX_RESULT);
            }
        });
        recyclerView.setAdapter(adapter);
        System.out.println("at set data, list size = " + cList.size());
        requestResults(0, 5);
        if (currentRefreshLayout != null) {
            currentRefreshLayout.refreshComplete();
            currentRefreshLayout = null;
        }
    }
}
