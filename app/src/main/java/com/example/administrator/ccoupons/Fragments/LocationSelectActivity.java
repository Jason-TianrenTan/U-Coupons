package com.example.administrator.ccoupons.Fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.ccoupons.R;

public class LocationSelectActivity extends AppCompatActivity {

    private String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);
        String localCity = getIntent().getStringExtra("location");
        if (localCity != null) {
            location = localCity;
        }

    }


}
