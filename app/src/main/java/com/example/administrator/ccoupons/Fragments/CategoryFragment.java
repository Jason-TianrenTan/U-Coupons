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
import com.example.administrator.ccoupons.Banner.NetworkImageHolderView;
import com.example.administrator.ccoupons.Category;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.SearchActivity;
import com.example.administrator.ccoupons.Tools.LocationGet;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.UI.CustomDialog;
import com.example.administrator.ccoupons.UI.CustomLoader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.crosswall.lib.coverflow.core.PageItemClickListener;

public class CategoryFragment extends Fragment {


    private RecyclerView recommendView;
    private CustomLoader customLoader;
    private ArrayList<Category> categoryList;
    private CategoryAdapter adapter;
    private TextView location_text;
    private ArrayList<String> networkImages;
    private ArrayList<Coupon> recommendList;
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
                    MyApp app = (MyApp) getActivity().getApplicationContext();
                    app.setLocation(location);
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

        initRecyclerViews(view);

        initRecommends();

        initBanner();

        initLocation();



        return view;
    }


    private void parseRecommendMessage(String response) {
        try {
            JSONObject mainObj = new JSONObject(response);
            JSONArray jsonArray = mainObj.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(obj);
                recommendList.add(coupon);
            }
            MainPageCouponAdapter adapter = new MainPageCouponAdapter(recommendList);
            recommendView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecommends() {
        String url = DataHolder.base_URL + DataHolder.postRecommend_URL;
        HashMap<String, String> map = new HashMap<>();
        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseRecommendMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {

            }

            @Override
            public void onConnectionFailed() {

            }
        });
        connectionManager.connect();
    }

    private void initRecyclerViews(View view) {
        //类别Recylcerview
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        recommendList = new ArrayList<>();
        recommendView = (RecyclerView) view.findViewById(R.id.recommend_recyclerview);
        LinearLayoutManager recLayoutManager = new LinearLayoutManager(getActivity());
        recommendView.setLayoutManager(recLayoutManager);
        MainPageCouponAdapter rec_adapter = new MainPageCouponAdapter(recommendList);
        recommendView.setAdapter(rec_adapter);
        recommendView.setNestedScrollingEnabled(false);
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
            ;
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

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
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
        locationFetchr = new LocationGet(getActivity(), handler);
        locationFetchr.requestLocation();
        customLoader = new CustomLoader(5, getActivity());
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
