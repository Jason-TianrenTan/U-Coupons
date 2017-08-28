package com.example.administrator.ccoupons.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Main.WelcomeActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;
import com.example.administrator.ccoupons.UI.GlideCircleTransform;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.UserBuyCoupons;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.UserFollowCoupons;
import com.example.administrator.ccoupons.User.UserInformationActivity;
import com.example.administrator.ccoupons.User.UserMyCouponActivity;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.UserSellCoupons;
import com.example.administrator.ccoupons.User.UserSettingActivity;
import com.example.administrator.ccoupons.User.UserWalletActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CZJ on 2017/7/13.
 */

public class UserOptionFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {


    @BindView(R.id.user_nickname_text)
    TextView userNickname;
    @BindView(R.id.main_linearlayout_title)
    FrameLayout mTitleContainer;
    @BindView(R.id.main_appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.user_ub)
    TextView userUcoin;
    @BindView(R.id.main_textview_title)
    TextView mTitle;
    @BindView(R.id.user_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_portrait)
    CircleImageView portrait;

    @OnClick({R.id.user_main_toolbar, R.id.user_nickname_text, R.id.user_to_mycoupons, R.id.user_to_wal, R.id.user_to_inf, R.id.user_sell, R.id.user_buy, R.id.user_follow, R.id.user_to_set, R.id.user_logoff, R.id.user_portrait})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_main_toolbar:
                getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
                break;
            case R.id.user_to_inf:
                getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
                break;
            case R.id.user_to_mycoupons:
                getActivity().startActivity(new Intent(getActivity(), UserMyCouponActivity.class));
                break;
            case R.id.user_to_wal:
                getActivity().startActivity(new Intent(getActivity(), UserWalletActivity.class));
                break;
            case R.id.user_sell:
                getActivity().startActivity(new Intent(getActivity(), UserSellCoupons.class));
                break;
            case R.id.user_buy:
                getActivity().startActivity(new Intent(getActivity(), UserBuyCoupons.class));
                break;
            case R.id.user_follow:
                getActivity().startActivity(new Intent(getActivity(), UserFollowCoupons.class));
                break;
            case R.id.user_to_set:
                getActivity().startActivity(new Intent(getActivity(), UserSettingActivity.class));
                break;
            case R.id.user_logoff:
                showLogOffDialog();
                break;
            case R.id.user_portrait:
                getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
                break;
        }
    }
    Unbinder unbinder;
    private UserInfoManager userInfoManager;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private int Ucoin;
    private String nickname;
    private String avatar_url;

    public void initViews() {
        userInfoManager = new UserInfoManager(getActivity());
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_option, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        initData();
        initPortrait();
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        return view;
    }

    private void initData() {
        MyApp app = (MyApp) getActivity().getApplicationContext();
        this.avatar_url = app.getAvatar();
        this.nickname = app.getNickname();
        this.Ucoin = app.getUcoin();
        userUcoin.setText(Ucoin + "");
        setNickname();
    }

    private void setNickname() {
        if (nickname.length() < 5) {
            userNickname.setTextSize(28);
        } else if (nickname.length() < 9) {
            userNickname.setTextSize(25);
        } else
            userNickname.setTextSize(22);
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
            Glide.with(getActivity())
                    .load(url)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(portrait);
        } else portrait.setImageResource(R.drawable.testportrait);
    }

    @Override
    public void onStart() {
        initPortrait();
        initData();
        super.onStart();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static UserOptionFragment newInstance() {
        return new UserOptionFragment();
    }
}
