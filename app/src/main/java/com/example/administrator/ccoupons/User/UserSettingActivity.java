package com.example.administrator.ccoupons.User;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserSettingActivity extends SlideBackActivity {

    @BindView(R.id.uset_toolbar)
    Toolbar toolbar;
    @BindView(R.id.uset_clear)
    LinearLayout clear;

    @OnClick(R.id.uset_clear)
    public void click(View view) {
        showClearDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ButterKnife.bind(this);
        initToolbar();
    }

    /**
     * Initially Toolbar
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
     * Show dialog confirming clear
     */
    private void showClearDialog() {
        final AlertDialog.Builder clearDialog =
                new AlertDialog.Builder(UserSettingActivity.this);
        clearDialog.setMessage("确定要清空搜索历史记录、用户登录信息以及图片等缓存?");
        clearDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清空登录信息
                        LoginInformationManager loginInformationManager =
                                new LoginInformationManager(UserSettingActivity.this);
                        loginInformationManager.clear();
                        //清空用户信息
                        //清空所有缓存内容
                        Toast.makeText(getApplicationContext(), "缓存已清除", Toast.LENGTH_SHORT).show();
                    }
                });
        clearDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        clearDialog.show();
    }
}
