package com.example.administrator.ccoupons.Things;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ThingPurchaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @BindView(R.id.thing_purchase_toolbar)
    Toolbar toolbar;
    @BindView(R.id.thing_purchase_listView)
    ListView thingPurchaseListView;
    @BindView(R.id.thing_purchase_thingsList)
    RecyclerView thingPurchaseThingsList;

    @OnClick(R.id.tv_thingpurchase_back)
    public void onClick() {
        finish();
    }


    private ArrayList<ThingsCategory> categoryList;
    private ThingsCategoryAdpater cat_adapter;
    private ThingAdapter thingAdapter;
    private ArrayList<Thing> thingArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_purchase);
        ButterKnife.bind(this);
        initToolBar();
        initCategory();
        initRecyclerViews();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * init category data
     */
    private void initCategory() {
        categoryList = new ArrayList<ThingsCategory>();
        for (int i = 0; i < GlobalConfig.ThingsCategories.thingsCovers.length; i++) {
            ThingsCategory category = new ThingsCategory(GlobalConfig.ThingsCategories.thingsList[i], GlobalConfig.ThingsCategories.thingsCovers[i]);
            categoryList.add(category);
        }

        cat_adapter = new ThingsCategoryAdpater(ThingPurchaseActivity.this, R.layout.thing_purchase_item, categoryList, 0);
        thingPurchaseListView.setAdapter(cat_adapter);

        thingPurchaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(((ThingsCategoryAdpater) parent.getAdapter()).setChosen(position)) {
                    cat_adapter.notifyDataSetChanged();
                    //切换recycleview的内容
                    thingArrayList.clear();
                    thingArrayList.addAll(FakeDatas.getData(position));
                    thingAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    private void initRecyclerViews() {
        LinearLayoutManager recLayoutManager = new LinearLayoutManager(this);
        thingPurchaseThingsList.setLayoutManager(recLayoutManager);
        thingArrayList = FakeDatas.getData(0);
        thingAdapter = new ThingAdapter(thingArrayList);
        thingPurchaseThingsList.setAdapter(thingAdapter);
        thingPurchaseThingsList.setNestedScrollingEnabled(false);
    }
}
