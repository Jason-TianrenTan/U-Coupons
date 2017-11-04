package com.example.administrator.ccoupons.AddCoupon;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.administrator.ccoupons.R;

import java.util.ArrayList;
import java.util.Iterator;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SecondAddActivity extends AddCouponBaseActivity {


    ImageView addButton;
    RecyclerView recyclerView;
    NestedScrollView scrollView;
    EditText discountEditText;
    boolean requestFocus = false;

    ArrayList<String> constraintList = new ArrayList<>();
    SConstraintsAdapter adapter;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void initViews() {
        super.setTopImage(1);
        super.inflateView(R.layout.second_add_view);
        scrollView = inflate_View.findViewById(R.id.add_constraint_scrollview);
        addButton = inflate_View.findViewById(R.id.s_add_constraint_button);
        discountEditText = inflate_View.findViewById(R.id.et_constraint_discount);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                int i = 0;
                for (String str:constraintList) {
                    System.out.println("cons" + i + ": " + str);
                    i++;
                }
            }
        });

        recyclerView = inflate_View.findViewById(R.id.form_constraints_recyclerview);
        adapter = new SConstraintsAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void NextOnClick() {
        String discount = discountEditText.getText().toString();
        if (discount != null && discount.length() > 0) {
            Intent intent = new Intent(SecondAddActivity.this, ThirdAddActivity.class);
            //TODO: add constrains
            Iterator<String> iterator = constraintList.iterator();
            while (iterator.hasNext()) {
                String str = iterator.next();
                if (str == null || str.length() == 0)
                    iterator.remove();
            }
            String[] constraint_array = new String[constraintList.size()];
            int i = 0;
            for (String str : constraintList) {
                constraint_array[i] = str;
                i++;
            }
            if (constraint_array.length > 0)
                intent.putExtra("constraints", constraint_array);
            Intent gI = getIntent();
            intent.putExtra("category", gI.getStringExtra("category"));
            intent.putExtra("product", gI.getStringExtra("product"));
            intent.putExtra("brand", gI.getStringExtra("brand"));
            intent.putExtra("expire", gI.getStringExtra("expire"));
            intent.putExtra("picture", gI.getStringExtra("picture"));
            intent.putExtra("discount", discount);
            startActivity(intent);
        }
        else
            makeToast("请输入优惠额度!");
    }


    //Adapter for RecyclerView in coupon's constraints
    public class SConstraintsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;

        public SConstraintsAdapter() {

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.constraint_item, parent, false);
            return new SConstraintsViewHolder(view);

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final String constraint = constraintList.get(position);
            final SConstraintsViewHolder viewHolder = (SConstraintsViewHolder) holder;
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
//                    adapter.notifyItemRemoved(position);
//                    adapter.notifyItemRangeChanged(position + 1, constraintList.size());
                    adapter = new SConstraintsAdapter();
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
            if (requestFocus && position == constraintList.size() - 1) {
                ((SConstraintsViewHolder) holder).editText.postDelayed(new Runnable() {
                    @Override

                    public void run() {
                        ((SConstraintsViewHolder) holder).editText.requestFocus();
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


        public class SConstraintsViewHolder extends RecyclerView.ViewHolder {
            LinearLayout rootView;
            EditText editText;
            ImageView deleteButton;

            public SConstraintsViewHolder(View view) {
                super(view);
                rootView = (LinearLayout) view;
                editText = (EditText) view.findViewById(R.id.form_constraint_edittext);
                deleteButton = (ImageView) view.findViewById(R.id.delete_constraint_button);
            }
        }
    }

}
