package com.example.administrator.ccoupons.AddCoupon;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Fragments.Category.Category;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * This interface is the sub interface for manually adding Coupons: select coupons
 */
public class ChooseCategoryActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private ArrayList<Category> mCList;
    private ChooseAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        initData();
        bindViews();
    }


    //init data
    private void initData() {
        mCList = new ArrayList<>();
        for (int i = 0; i< GlobalConfig.Categories.covers.length; i++) {
            int resId = GlobalConfig.Categories.covers[i];
            String name = GlobalConfig.Categories.nameList[i];
            Category category = new Category(name, resId);
            mCList.add(category);
        }

    }


    //bind views
    private void bindViews() {
        recyclerView = (RecyclerView) findViewById(R.id.category_choose_recyclerview);
        adapter = new ChooseAdapter(mCList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.category_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    //adapter for choosing category
    public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ChooseViewHolder> {

        private Context mContext;
        private ArrayList<Category> categoryList;

        public class ChooseViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            LinearLayout rootView;

            public ChooseViewHolder(View view) {
                super(view);
                rootView = (LinearLayout) view;
                imageView = (ImageView) view.findViewById(R.id.choose_item_img);
                textView = (TextView) view.findViewById(R.id.choose_item_title);
            }
        }


        public ChooseAdapter(ArrayList<Category> cList) {
            this.categoryList = cList;
        }

        @Override
        public ChooseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.choose_category_item, parent, false);
            ChooseViewHolder holder = new ChooseViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ChooseViewHolder holder, final int position) {
            Category category = categoryList.get(position);
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), category.getResId()));
            holder.textView.setText(category.getProduct());
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(position, getIntent());
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }
    }

}
