package com.example.administrator.ccoupons.User;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

<<<<<<< HEAD
import com.example.administrator.ccoupons.Data.DataHolder;
=======
import com.example.administrator.ccoupons.MyApp;
>>>>>>> ttr
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserWalletActivity extends SlideBackActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

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
<<<<<<< HEAD
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
=======
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
>>>>>>> Czj
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
<<<<<<< HEAD
}
=======


    /**
     * init Ucoin data
     */
    private void initData() {
        MyApp app = (MyApp) getApplicationContext();
        ub.setText(String.format("%.2f", (float)app.getUcoin()));
    }
}
>>>>>>> Czj
