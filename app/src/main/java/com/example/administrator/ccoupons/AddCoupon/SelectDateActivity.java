package com.example.administrator.ccoupons.AddCoupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.ccoupons.R;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * This interface is the sub interface for manually adding Coupons: select expiration dates
 */
public class SelectDateActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @BindView(R.id.form_datepicker)
    DatePicker datePicker;
    public static int RESULT_NORMAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        ButterKnife.bind(this);
        datePicker.setDate(2017, 9);
        datePicker.setMode(DPMode.SINGLE);
        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Intent intent = new Intent();
                intent.putExtra("date", date);
                setResult(RESULT_NORMAL, intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
