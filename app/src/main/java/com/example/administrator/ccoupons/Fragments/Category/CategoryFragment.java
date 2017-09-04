package com.example.administrator.ccoupons.Fragments.Category;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.administrator.ccoupons.Banner.NetworkImageHolderView;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.LocationSelectActivity;
import com.example.administrator.ccoupons.Fragments.MainPageCouponAdapter;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.SearchActivity;
import com.example.administrator.ccoupons.Tools.LocationGet;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zyao89.view.zloading.ZLoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class CategoryFragment extends Fragment {


    public static final int FOOTER_LOADMORE = 1,
            FOOTER_ENDOFLIST = 0;

    @BindView(R.id.loading_view)
    ZLoadingView loadingView;
    @BindView(R.id.location_textview)
    TextView locationTextview;
    @BindView(R.id.category_message_button)
    Button categoryMessageButton;
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.search_input_layout)
    TextInputLayout searchInputLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.category_banner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.category_recycler_view)
    RecyclerView categoryView;
    @BindView(R.id.recommend_recyclerview)
    RecyclerView recommendView;
    @BindView(R.id.main_nestedscrollview)
    NestedScrollView mainNestedscrollview;
    @BindView(R.id.category_ptr_frame)
    PtrFrameLayout categoryPtrFrame;

    PtrFrameLayout currentRefreshLayout = null;
    @BindView(R.id.fab_action_fillform)
    FloatingActionButton fillFormFab;
    @BindView(R.id.fab_action_scanqr)
    FloatingActionButton scanQRFab;
    @BindView(R.id.multiple_actions)
    FloatingActionsMenu fab_menu;

    @BindView(R.id.category_rootview)
    LinearLayout rootView;

    @OnClick({R.id.location_textview, R.id.category_message_button})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.location_textview:
                Intent intent = new Intent(getActivity(), LocationSelectActivity.class);
                if (location != null)
                    intent.putExtra("location", location);
                startActivity(intent);
                break;
            case R.id.category_message_button:
                //getActivity().startActivity(new Intent(getActivity(), MyMessageActivity.class));
                break;
        }

    }

    private Unbinder unbinder;
    private ArrayList<Category> categoryList;
    private CategoryAdapter cat_adapter;
    private MainPageCouponAdapter rec_adapter;
    private ArrayList<String> networkImages;
    private ArrayList<Coupon> recommendList;
    private ArrayList<Coupon> fullRecList;
    private String location = null;

    /**
     * @param clistEvent recommend list
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponListEvent clistEvent) {
        fullRecList = clistEvent.getList();
        requestData(0, 4);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.setVisibility(View.INVISIBLE);
            }
        }, 1000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        toolbar.setTitleTextColor(Color.WHITE);
        searchText.setFocusable(false);
        searchText.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        initCategory();
        initRecyclerViews(view);
        initPTR();
        initFAB(view);

        new UniversalPresenter().getRecommendByRxRetrofit();
        initBanner();
        initLocation();
        return view;
    }


    private void initFAB(final View view) {

    }



    private void initRecyclerViews(View view) {
        categoryView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        categoryView.setLayoutManager(layoutManager);
        cat_adapter = new CategoryAdapter(categoryList);
        categoryView.setAdapter(cat_adapter);
        categoryView.setNestedScrollingEnabled(false);

        recommendList = new ArrayList<>();
        recommendView = (RecyclerView) view.findViewById(R.id.recommend_recyclerview);
        LinearLayoutManager recLayoutManager = new LinearLayoutManager(getActivity());
        recommendView.setLayoutManager(recLayoutManager);
        rec_adapter = new MainPageCouponAdapter(recommendList);
        recommendView.setAdapter(rec_adapter);
        recommendView.setNestedScrollingEnabled(false);


        mainNestedscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                fab_menu.collapse();
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        requestData(recommendList.size(), 4);
                    }
                }
            }
        });
    }


    private void initPTR() {
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        header.setPadding(0, PixelUtils.dp2px(getActivity(), 15), 0, 0);
        categoryPtrFrame.setHeaderView(header);
        categoryPtrFrame.addPtrUIHandler(header);
        categoryPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentRefreshLayout = frame;
                //    categoryAppbar.setVisibility(View.INVISIBLE);
                new UniversalPresenter().getRecommendByRxRetrofit();
            }
        });
    }


    //设置footer
    private void setFooterView(int type) {
        switch (type) {
            case FOOTER_LOADMORE:
                View footer = LayoutInflater.from(getActivity()).inflate(R.layout.load_footer, recommendView, false);
                rec_adapter.setFooterView(footer);
                break;
        }

    }

    private void requestData(int start, int ceiling) {
        int count = 0;
        try {
            for (int i = start; i < fullRecList.size(); i++, count++) {
                if (count >= ceiling)
                    break;
                Coupon coupon = fullRecList.get(i);
                recommendList.add(coupon);
            }
            if (recommendList.size() < fullRecList.size()) {
                setFooterView(FOOTER_LOADMORE);
            } else rec_adapter.setFooterView(null);
            rec_adapter.notifyDataSetChanged();
            if (currentRefreshLayout != null)
                currentRefreshLayout.refreshComplete();
            //   categoryAppbar.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseBannerMessage(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                String url = jsonArray.getString(i);
                String nurl = DataHolder.base_URL + "/static/" + url;
                networkImages.add(nurl);
            }
            initImageLoader();
            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, networkImages).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //{"result": ["images/banner/banner_1.jpg", "images/banner/banner_2.jpg", "images/banner/banner_3.jpg", "images/banner/banner_4.jpg", "images/banner/banner_5.jpg"]}
    //初始化展示板
    private void initBanner() {
        networkImages = new ArrayList<String>();
        //post
        String url = DataHolder.base_URL + DataHolder.postBanner_URL;
        HashMap<String, String> map = new HashMap<>();
        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseBannerMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {

            }

            @Override
            public void onConnectionFailed() {

            }
        });
        connectionManager.connect();
        convenientBanner.setScrollDuration(1200);
        convenientBanner.startTurning(2000);

    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.mascot_nothing)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }


    //初始化数据
    private void initCategory() {
        categoryList = new ArrayList<Category>();
        for (int i = 0; i < DataHolder.Categories.covers.length; i++) {
            Category category = new Category(DataHolder.Categories.nameList[i], DataHolder.Categories.covers[i]);
            categoryList.add(category);
        }
    }


    public void initLocation() {
        (new LocationGet(getActivity(), locationTextview)).requestLocation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }


    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

}
