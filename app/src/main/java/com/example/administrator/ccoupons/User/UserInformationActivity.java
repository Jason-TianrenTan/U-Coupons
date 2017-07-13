package com.example.administrator.ccoupons.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.XCRoundImageView;

public class UserInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.uinf_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView name = (TextView) findViewById(R.id.user_name);
        TextView sex = (TextView) findViewById(R.id.user_sex);
        TextView age = (TextView) findViewById(R.id.user_age);
        XCRoundImageView portrait = (XCRoundImageView) findViewById(R.id.uinf_portrait);
        name.setText(DataHolder.User.username);
        age.setText(Integer.toString(DataHolder.User.age));
        if (DataHolder.User.sex)
            sex.setText("男");
        else
            sex.setText("女");
        portrait.setImageResource(DataHolder.User.portraitId);
    }
}