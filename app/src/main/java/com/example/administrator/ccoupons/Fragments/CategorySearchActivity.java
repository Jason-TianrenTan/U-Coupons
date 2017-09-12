package com.example.administrator.ccoupons.Fragments;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.SearchActivity;
import com.example.administrator.ccoupons.Tools.LocationGet;
import com.example.administrator.ccoupons.Tools.MessageType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategorySearchActivity extends AppCompatActivity {


    @BindView(R.id.category_title)
    TextView categoryText;
    @BindView(R.id.cat_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.category_location_textview)
    TextView locationText;
    @BindView(R.id.pos_img)
    ImageView posImg;
    @BindView(R.id.cat_input_search)
    EditText editText;
    @BindView(R.id.category_main_recyclerview)
    RecyclerView recyclerView;
    private String location;
    private LocationGet locationFetchr;
    private MainPageCouponAdapter adapter;
    private ArrayList<Coupon> recommendList;
    private String catId;
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MessageType.LOCATION_GET:
                    location = locationFetchr.getCity();
                    locationText.setText(location);
                    ((MyApp) getApplicationContext()).setLocation(location);
                    break;
                case MessageType.LOCATION_FAILED:
                    Toast.makeText(getApplicationContext(), "获取定位信息失败!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private void initLocation() {
        String str = ((MyApp) getApplicationContext()).getLocation();
        if (str != null && str.length() > 0) {
            locationText.setText(str);
        } else {
            locationFetchr = new LocationGet(this, locationText);
            locationFetchr.requestLocation();
        }
    }

    private void bindViews() {
        categoryText.setText(GlobalConfig.Categories.nameList[Integer.parseInt(catId) - 1]);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategorySearchActivity.this, SearchActivity.class);
                intent.putExtra("type", catId);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_search);
        ButterKnife.bind(this);

        catId = getIntent().getStringExtra("categoryId");
        bindViews();
        initRecyclerView();
        initLocation();
    }



    private void initRecyclerView() {
        recommendList = new ArrayList<>();
        requestData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainPageCouponAdapter(recommendList);
        recyclerView.setAdapter(adapter);
    }

    //请求推送数据
    private void requestData() {
        String url = GlobalConfig.base_URL + GlobalConfig.requestCatRecommend_URL;
        HashMap<String, String> map = new HashMap<>();
        map.put("categoryID", catId);

        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
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


    private void parseMessage(String response) {
        try {
            JSONObject mainObj = new JSONObject(response);
            JSONArray jsonArray = mainObj.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(obj);
                recommendList.add(coupon);
            }
            MainPageCouponAdapter adapter = new MainPageCouponAdapter(recommendList);
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
