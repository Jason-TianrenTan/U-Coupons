package com.example.administrator.ccoupons.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserPortraitActivity;
import com.example.administrator.ccoupons.User.UserInformationActivity;

/**
 * Created by CZJ on 2017/7/13.
 */

public class UserOptionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_option, container, false);
        LinearLayout toUserInfo = (LinearLayout) view.findViewById(R.id.user_top);
        toUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
            }
        });
        ImageView userportait = (ImageView) view.findViewById(R.id.user_portrait);
        userportait.setImageResource(DataHolder.User.portraitId);
        userportait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserPortraitActivity.class));
            }
        });
        TextView ub = (TextView) view.findViewById(R.id.user_ub);
        ub.setText(Integer.toString(DataHolder.User.UB));
        return view;
    }
}
