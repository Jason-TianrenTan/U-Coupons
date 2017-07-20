package com.example.administrator.ccoupons.Fragments;

import com.amap.api.location.AMapLocation;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.LinkagePager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.SearchActivity;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {


    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private ArrayList<Category> categoryList;
    private CategoryAdapter adapter;
    private TextView location_text;
    private ArrayList<Integer> localImages;
    private ConvenientBanner convenientBanner;
    //TODO:handler

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_category, container, false);

        convenientBanner = (ConvenientBanner) view.findViewById(R.id.category_banner);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        location_text = (TextView) view.findViewById(R.id.location_textview);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) location_text.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.search_input_layout);
        location_text.setLayoutParams(params);
        location_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = location_text.getText().toString();
                Intent intent = new Intent(getActivity(), LocationSelectActivity.class);
                if (location != null) {
                    intent.putExtra("location", location);

                }
                startActivity(intent);
            }
        });


        EditText searchText = (EditText) view.findViewById(R.id.search_text);
        searchText.setFocusable(false);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        //   SearchView searchView = (SearchView)view.findViewById(R.id.search_view);
        initCategory();
        initBanner();
        initLocation();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);//测试
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        return view;
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
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setHttpTimeOut(20 * 1000);
        mLocationOption.setNeedAddress(true);

        AMapLocationListener aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String address = aMapLocation.getCity();
                        location_text.setText(address);
                    }
                }
            }
        };
        mLocationClient.setLocationListener(aMapLocationListener);

        mLocationClient.startLocation();
    }


}
