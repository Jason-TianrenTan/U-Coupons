package com.example.administrator.ccoupons.AddCoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.ccoupons.R;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class SelectDateActivity extends AppCompatActivity {

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
}
