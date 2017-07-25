package com.example.administrator.ccoupons.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;

public class UserWalletActivity extends SlideBackActivity {
    private Toolbar toolbar;
    private TextView ub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet);
        initView();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.uwal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ub = (TextView) findViewById(R.id.uwal_ub);
        ub.setText(Integer.toString(DataHolder.User.UB));
    }

    private void setOnClickListeners(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
