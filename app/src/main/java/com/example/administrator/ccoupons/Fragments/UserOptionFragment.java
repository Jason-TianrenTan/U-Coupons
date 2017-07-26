package com.example.administrator.ccoupons.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.WelcomeActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.User.UserBuyCoupons;
import com.example.administrator.ccoupons.User.UserFollowCoupon;
import com.example.administrator.ccoupons.User.UserSellCoupons;
import com.example.administrator.ccoupons.User.UserMyCouponActivity;
import com.example.administrator.ccoupons.User.UserInformationActivity;
import com.example.administrator.ccoupons.User.UserSettingActivity;
import com.example.administrator.ccoupons.User.UserWalletActivity;

/**
 * Created by CZJ on 2017/7/13.
 */

public class UserOptionFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {
    private UserInfoManager userInfoManager;
    private ImageView portrait;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private FrameLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar toolbar;

    private int Ucoin;
    private String nickname;
    private String avatar_url;
    private LinearLayout toUserMyCoupons, toUserWal, toSetting, toUserSell, toUserBuy, toUserFollow, logoff;
    private TextView userUcoin, userNickname;
    private CollapsingToolbarLayout toUserInfo;

    public void bindViews(View view) {
        portrait = (ImageView) view.findViewById(R.id.user_portrait);
        userUcoin = (TextView) view.findViewById(R.id.user_ub);
        userNickname = (TextView) view.findViewById(R.id.user_nickname_text);
        toUserMyCoupons = (LinearLayout) view.findViewById(R.id.user_to_mycoupons);
        toUserWal = (LinearLayout) view.findViewById(R.id.user_to_wal);
        toSetting = (LinearLayout) view.findViewById(R.id.user_to_set);
        logoff = (LinearLayout) view.findViewById(R.id.user_logoff);
        toUserSell = (LinearLayout) view.findViewById(R.id.user_sell);
        toUserBuy = (LinearLayout) view.findViewById(R.id.user_buy);
        toUserFollow = (LinearLayout) view.findViewById(R.id.user_follow);
        toUserInfo = (CollapsingToolbarLayout) view.findViewById(R.id.user_to_inf);
        mTitle = (TextView) view.findViewById(R.id.main_textview_title);
        mTitleContainer = (FrameLayout) view.findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.main_appbar);
        toolbar = (Toolbar) view.findViewById(R.id.user_main_toolbar);

        userInfoManager = new UserInfoManager(getActivity());
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    private void setListeners() {
        //OnClickListener
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
            }
        });
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
            }
        });
        toUserMyCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), UserMyCouponActivity.class));
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
        toUserSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserSellCoupons.class));
            }
        });
        toUserBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserBuyCoupons.class));
            }
        });
        toUserFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserFollowCoupon.class));
            }
        });
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogOffDialog();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_option, container, false);
        bindViews(view);
        setListeners();
        initData();
        initPortrait();
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        return view;
    }

    private void initData() {
        MyApp app = (MyApp) getActivity().getApplicationContext();
        this.nickname = app.getNickname();
        this.avatar_url = app.getAvatar();
        this.Ucoin = app.getUcoin();
        userUcoin.setText(Ucoin + "");
        userNickname.setText(nickname);
    }

    private void showLogOffDialog() {
        final AlertDialog.Builder logOffDialog =
                new AlertDialog.Builder(getActivity());
        logOffDialog.setMessage("确定要退出当前账户?");
        logOffDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginInformationManager loginInformationManager =
                                new LoginInformationManager(getActivity());
                        loginInformationManager.setAutoLogin(false).removePassword();
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

    public void initPortrait() {
        MyApp app = (MyApp) getActivity().getApplicationContext();
        String url = app.getAvatar();
        System.out.println("avatar = " + url);
        if (url != "") {
            ImageManager.GlideImage(url, portrait, getActivity().getApplicationContext());
        } else portrait.setImageResource(R.drawable.testportrait);
    }

    @Override
    public void onStart() {
        super.onStart();
        initPortrait();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
