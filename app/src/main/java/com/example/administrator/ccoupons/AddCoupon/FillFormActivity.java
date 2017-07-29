package com.example.administrator.ccoupons.AddCoupon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.administrator.ccoupons.CustomEditText.ClearableEditText;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;

import java.util.ArrayList;
import java.util.List;

public class FillFormActivity extends AppCompatActivity {

    private EditText categoryEditText;
    private ClearableEditText productNameText,
                            brandNameText,
                            discountText;
    private RecyclerView recyclerView;
    private ConstraintsAdapter adapter;
    private NestedScrollView scrollView;
    private void bindViews() {
        categoryEditText = (EditText) findViewById(R.id.form_category_edittext);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.arrow);
        drawable.setBounds(0, 0, 40, 40);
        categoryEditText.setCompoundDrawables(null, null, drawable, null);
        scrollView = (NestedScrollView) findViewById(R.id.form_scrollview);
        productNameText = (ClearableEditText) findViewById(R.id.form_product_edittext);
        brandNameText = (ClearableEditText) findViewById(R.id.form_brand_edittext);
        discountText = (ClearableEditText) findViewById(R.id.form_discount_edittext);
        recyclerView = (RecyclerView) findViewById(R.id.form_constraints_recyclerview);
        final ArrayList<String> strList = new ArrayList<>();
        strList.add("");
        strList.add("");
        strList.add("");
        strList.add("EOF");
        //TODO: adapter

        adapter = new ConstraintsAdapter(strList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form);

        bindViews();
    }




    public class ConstraintsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public static final int EDITEXT_TYPE = 1;
        public static final int PLUS_TYPE = 0;
        private Context mContext;
        private ArrayList<String> constraintList;

        public ConstraintsAdapter(ArrayList<String> cList) {
            this.constraintList = cList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view;
            if (viewType == EDITEXT_TYPE) {
                view = LayoutInflater.from(mContext).inflate(R.layout.constraint_item, parent, false);
                return new ConstraintsViewHolder(view);
            }
            if (viewType == PLUS_TYPE) {
                view = LayoutInflater.from(mContext).inflate(R.layout.addconstraint_item, parent, false);
                return new AddConstraintViewHolder(view);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemViewType(int position) {
            if (position == (constraintList.size() - 1))
                return PLUS_TYPE;
            return EDITEXT_TYPE;
        }

        @Override
        public int getItemCount() {
            return constraintList.size();
        }



        public class ConstraintsViewHolder extends RecyclerView.ViewHolder {
            TextInputLayout inputLayout;
            public ConstraintsViewHolder(View view) {
                super(view);
                inputLayout = view.findViewById(R.id.constraint_inputlayout);
            }
        }
        //添加限制ViewHolder
        public class AddConstraintViewHolder extends RecyclerView.ViewHolder {

            private FrameLayout addButton;
            public AddConstraintViewHolder(View view) {
                super(view);
                addButton = (FrameLayout)view;
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int size = constraintList.size();
                        constraintList.add(size - 1, "");
                        adapter.notifyDataSetChanged();
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                });
            }
        }
    }
}
