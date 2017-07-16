package com.example.administrator.ccoupons.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.WelcomeActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserPortraitActivity;
import com.example.administrator.ccoupons.User.UserInformationActivity;
import com.example.administrator.ccoupons.User.UserSettingActivity;
import com.example.administrator.ccoupons.User.UserWalletActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CZJ on 2017/7/13.
 */

public class UserOptionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_option, container, false);
        ImageView userportait = (ImageView) view.findViewById(R.id.user_portrait);
        userportait.setImageResource(DataHolder.User.portraitId);
        TextView ub = (TextView) view.findViewById(R.id.user_ub);
        ub.setText(Integer.toString(DataHolder.User.UB));
        LinearLayout toUserInfo = (LinearLayout) view.findViewById(R.id.user_to_inf);
        LinearLayout toUserWal = (LinearLayout) view.findViewById(R.id.user_to_wal);
        LinearLayout toSetting = (LinearLayout) view.findViewById(R.id.user_to_set);
        LinearLayout logoff = (LinearLayout) view.findViewById(R.id.user_logoff);

        //OnClickListener
        userportait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserPortraitActivity.class));
            }
        });
        toUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
            }
        });
        toUserWal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserWalletActivity.class));
            }
        });
        toSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserSettingActivity.class));
            }
        });
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogOffDialog();
            }
        });


        return view;
    }

    private void showLogOffDialog() {
        final AlertDialog.Builder logOffDialog =
                new AlertDialog.Builder(getActivity());
        logOffDialog.setMessage("确定要退出当前账户?");
        logOffDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("UserInfomation", MODE_PRIVATE).edit();
                        editor.putBoolean("auto_login", false).remove("password").commit();
                        startActivity(new Intent(getActivity(), WelcomeActivity.class));
                        getActivity().finish();
                    }
                });
        logOffDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        logOffDialog.show();
    }
}
