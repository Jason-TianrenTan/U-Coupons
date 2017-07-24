package com.example.administrator.ccoupons.Fragments;

import com.amap.api.location.AMapLocation;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.*;
import android.os.Message;
import android.support.v4.view.LinkagePager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.administrator.ccoupons.Banner.BannerPicture;
import com.example.administrator.ccoupons.Banner.LocalImageHolderView;
import com.example.administrator.ccoupons.Category;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.SearchActivity;
import com.example.administrator.ccoupons.Tools.LocationGet;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.UI.CustomDialog;
import com.example.administrator.ccoupons.UI.CustomLoader;

import java.util.ArrayList;
import java.util.Random;

public class CategoryFragment extends Fragment {


    private CustomLoader customLoader;
    private ArrayList<Category> categoryList;
    private ArrayList<Coupon> RCouponList;
    private CategoryAdapter adapter;
    private TextView location_text;
    private ArrayList<Integer> localImages;
    private ConvenientBanner convenientBanner;
    private String location = null;
    private LocationGet locationFetchr;
    //TODO:handler
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MessageType.LOCATION_GET:
                    location = locationFetchr.getCity();
                    location_text.setText(location);
                    customLoader.finish();
                    break;
                case MessageType.LOCATION_FAILED:
                    Toast.makeText(getActivity().getApplicationContext(), "获取当前定位失败，请检查设置或者网络连接",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_category, container, false);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.category_banner);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        location_text = (TextView) view.findViewById(R.id.location_textview);
        location_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocationSelectActivity.class);
                if (location != null)
                    intent.putExtra("location", location);
                
                startActivity(intent);
            }
        });


        EditText searchText = (EditText) view.findViewById(R.id.search_text);
        searchText.setFocusable(false);
        searchText.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        TextView messageButton = (TextView) view.findViewById(R.id.category_message_button);
        messageButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), MyMessageActivity.class));
            }
        });

        //   SearchView searchView = (SearchView)view.findViewById(R.id.search_view);
        initCategory();

        initRecommends();

        initBanner();

        initLocation();

        initRecyclerViews(view);

        return view;
    }

    String[] urls = ("http://dxcb2.leiting.com/static/pc/images/occupation/zs/4.png?v=201706025," +
            "http://dxcb2.leiting.com/static/pc/images/occupation/zs/3.png?v=201706025," +
            "http://dxcb2.leiting.com/static/pc/images/occupation/sz/4.png?v=201706025," +
            "http://dxcb2.leiting.com/static/pc/images/occupation/sz/5.png?v=201706025," +
            "http://dxcb2.leiting.com/static/pc/images/occupation/fs/4.png?v=201706025," +
            "http://dxcb2.leiting.com/static/pc/images/occupation/qxz/4.png?v=201706025," +
            "http://dxcb2.leiting.com/static/pc/images/occupation/qxz/5.png?v=201706025").split(",");
    String[] coupon_names = "云之国剑士,无名的剑士,大力小萝莉,力魔圣骑士,黑袍术士,夜魇暗潮,诡术猎手".split(",");

    private void initRecommends() {
        RCouponList = new ArrayList<>();
        //TODO:测试
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Coupon coupon = new Coupon();

            coupon.setDetail("2017-7-23");
            coupon.setListPrice(198.0);
            int index = random.nextInt(urls.length);
            coupon.setName("SS招募券 - " + coupon_names[index]);
            coupon.setImgURL(urls[index]);
            RCouponList.add(coupon);
        }
    }

    private void initRecyclerViews(View view) {
        //类别Recylcerview
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        //推荐
        RecyclerView recommendView = (RecyclerView) view.findViewById(R.id.recommend_recyclerview);
        LinearLayoutManager recLayoutManager = new LinearLayoutManager(getActivity());
        recommendView.setLayoutManager(recLayoutManager);
        MainPageCouponAdapter rec_adapter = new MainPageCouponAdapter(RCouponList);
        recommendView.setAdapter(rec_adapter);
        recommendView.setNestedScrollingEnabled(false);
    }

    //初始化展示板
    private void initBanner() {
        localImages = new ArrayList<>();
        for (int i = 0; i < DataHolder.Banners.covers.length; i++)
            localImages.add(DataHolder.Banners.covers[i]);

        //本地图片例子

        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件

        convenientBanner.setScrollDuration(1200);
        convenientBanner.startTurning(2000);

    }

    //初始化标题栏

    //初始化数据
    private void initCategory() {
        categoryList = new ArrayList<Category>();
        for (int i = 0; i < DataHolder.Categories.covers.length; i++) {
            Category category = new Category(DataHolder.Categories.nameList[i], DataHolder.Categories.covers[i]);
            categoryList.add(category);
        }
    }

    public void initLocation() {
        locationFetchr = new LocationGet(getActivity(), handler);
        locationFetchr.requestLocation();
        customLoader = new CustomLoader(5, handler, getActivity());
        customLoader.setLoaderListener(new CustomLoader.CustomLoaderListener() {
            @Override
            public void onTimeChanged() {

            }

            @Override
            public void onTimeFinish() {
                android.os.Message msg = new android.os.Message();
                msg.what = MessageType.LOCATION_FAILED;
                handler.sendMessage(msg);
            }

        });
        customLoader.start();
    }

}
