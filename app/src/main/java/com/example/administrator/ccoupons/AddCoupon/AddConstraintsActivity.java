package com.example.administrator.ccoupons.AddCoupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddConstraintsActivity extends AppCompatActivity {


    boolean requestFocus = false;
    ArrayList<String> constraintList = new ArrayList<>();
    Coupon coupon;
    ConstraintsAdapter adapter;
    @BindView(R.id.form_constraints_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.constraint_scrollview)
    NestedScrollView scrollView;

    @OnClick(R.id.constraint_next_button)
    public void onClick() {
        Intent intent = new Intent(AddConstraintsActivity.this, AddCouponActivity.class);
        //TODO: add constrains
        Iterator<String> iterator = constraintList.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (str == null || str.length() == 0)
                iterator.remove();
        }
        System.out.println("after remove, size = " + constraintList.size());
        String[] constraint_array = new String[constraintList.size()];
        int i = 0;
        for (String str : constraintList) {
            constraint_array[i] = str;
            i++;
        }
        coupon.setConstraints(constraint_array);
        intent.putExtra("coupon", coupon);
        startActivity(intent);
    }


    @OnClick(R.id.add_constraint_button)
    public void onClick1() {
        int size = constraintList.size();
        constraintList.add("");
        requestFocus = true;
        adapter.notifyItemInserted(size);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_constraints);
        ButterKnife.bind(this);
        coupon = (Coupon) getIntent().getSerializableExtra("coupon");

        adapter = new ConstraintsAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    //Adapter for RecyclerView in coupon's constraints
    public class ConstraintsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;

        public ConstraintsAdapter() {

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.constraint_item, parent, false);
            return new ConstraintsViewHolder(view);

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final String constraint = constraintList.get(position);
            final ConstraintsViewHolder viewHolder = (ConstraintsViewHolder) holder;
            viewHolder.editText.setText(constraintList.get(position));
            viewHolder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    System.out.println("current change at index = " + holder.getAdapterPosition());
                    constraintList.set(holder.getAdapterPosition(), viewHolder.editText.getText().toString());
                }
            });
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Delete index " + position);
                    constraintList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(0, constraintList.size());
                }
            });
            if (requestFocus && position == constraintList.size() - 1) {
                ((ConstraintsViewHolder) holder).editText.postDelayed(new Runnable() {
                    @Override

                    public void run() {
                        ((ConstraintsViewHolder) holder).editText.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }, 300);
            }
        }


        @Override
        public int getItemCount() {
            return constraintList.size();
        }


        public class ConstraintsViewHolder extends RecyclerView.ViewHolder {
            LinearLayout rootView;
            EditText editText;
            ImageView deleteButton;

            public ConstraintsViewHolder(View view) {
                super(view);
                rootView = (LinearLayout) view;
                editText = (EditText) view.findViewById(R.id.form_constraint_edittext);
                deleteButton = (ImageView) view.findViewById(R.id.delete_constraint_button);
            }
        }
    }
}
