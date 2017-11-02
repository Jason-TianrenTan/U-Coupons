package com.example.administrator.ccoupons.User;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserWalletActivity extends SlideBackActivity {
    @BindView(R.id.uwal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.uwal_ub)
    TextView ub;
    @BindView(R.id.user_wallet_about)
    TextView about;

    @OnClick({R.id.uwal_toolbar, R.id.user_wallet_about})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.uwal_toolbar:
                break;
            case R.id.user_wallet_about:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet);
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }


    /**
     * init toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * init Ucoin data
     */
    private void initData() {
        ub = (TextView) findViewById(R.id.uwal_ub);
        MyApp app = (MyApp) getApplicationContext();
        ub.setText(app.getUcoin() + "");
    }
}