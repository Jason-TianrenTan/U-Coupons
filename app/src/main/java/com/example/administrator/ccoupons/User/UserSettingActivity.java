package com.example.administrator.ccoupons.User;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;

public class UserSettingActivity extends SlideBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.uset_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout clear = (LinearLayout) findViewById(R.id.uset_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showClearDialog();
            }
        });
    }

    private void showClearDialog() {
        final AlertDialog.Builder clearDialog =
                new AlertDialog.Builder(UserSettingActivity.this);
        clearDialog.setMessage("确定要清空所有应用缓存（图片、优惠券信息、用户信息等）?");
        clearDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清空登录信息
                        SharedPreferences.Editor editor = UserSettingActivity.this.getSharedPreferences("UserInfomation", MODE_PRIVATE).edit();
                        editor.clear().commit();
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
