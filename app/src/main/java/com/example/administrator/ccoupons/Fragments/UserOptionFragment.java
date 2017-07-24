package com.example.administrator.ccoupons.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.WelcomeActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.User.UserMyCouponActivity;
import com.example.administrator.ccoupons.User.UserPortraitActivity;
import com.example.administrator.ccoupons.User.UserInformationActivity;
import com.example.administrator.ccoupons.User.UserSettingActivity;
import com.example.administrator.ccoupons.User.UserWalletActivity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CZJ on 2017/7/13.
 */

public class UserOptionFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener{
    private LoginInformationManager informationManager;
    private ImageView portrait;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_option, container, false);
        portrait = (ImageView) view.findViewById(R.id.user_portrait     );
        TextView ub = (TextView) view.findViewById(R.id.user_ub);
        ub.setText(Integer.toString(DataHolder.User.UB));
        LinearLayout toUserMyCoupons = (LinearLayout)view.findViewById(R.id.user_to_mycoupons);
      //  LinearLayout toUserInfo = (LinearLayout) view.findViewById(R.id.user_to_inf);
        LinearLayout toUserWal = (LinearLayout) view.findViewById(R.id.user_to_wal);
        LinearLayout toSetting = (LinearLayout) view.findViewById(R.id.user_to_set);
        LinearLayout logoff = (LinearLayout) view.findViewById(R.id.user_logoff);
        mTitle          = (TextView) view.findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) view.findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) view.findViewById(R.id.main_appbar);
        informationManager = new LoginInformationManager(getActivity());
        mAppBarLayout.addOnOffsetChangedListener(this);
        initPortrait();

        //OnClickListener
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserPortraitActivity.class));
                getActivity().overridePendingTransition(R.anim.portrait_in, R.anim.noanim);
            }
        });
        toUserMyCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), UserMyCouponActivity.class));
            }
        });
     /*   toUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
            }
        });*/
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
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
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
        String s = informationManager.getPortraitPath();
        if (s != "") {
            Bitmap bitmap = BitmapFactory.decodeFile(s);
            portrait.setImageBitmap(bitmap);
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

            if(!mIsTheTitleVisible) {
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
            if(mIsTheTitleContainerVisible) {
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

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
/*
        private Context mContext;
        private List<UserOptionClass> options;
        private static final int COUPON = 1;
        private static final int WALLET = 2;
        private static final int SELL = 3;
        private static final int BUY = 4;
        private static final int SETTING = 5;
        private static final int HELP = 6;
        private static final int LOGOFF = 7;

        public class UserOptionAdpater extends RecyclerView.Adapter<UserOptionAdpater.UserOptionViewHolder> {

            public class UserOptionViewHolder extends RecyclerView.ViewHolder {
                ImageView icon;
                TextView option;
                View optionview;

                public UserOptionViewHolder(View view) {
                    super(view);
                    optionview = view;
                    icon = (ImageView) view.findViewById(R.id.uopt_icon);
                    option = (TextView) view.findViewById(R.id.uopt_text);
                }
            }

            public UserOptionAdpater(List<UserOptionClass> opts) {
                options = opts;
            }

            @Override
            public UserOptionAdpater.UserOptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (mContext == null) {
                    mContext = parent.getContext();
                }
                View view = LayoutInflater.from(mContext)
                        .inflate(R.layout.user_option_item, parent, false);
                final UserOptionViewHolder holder = new UserOptionViewHolder(view);
                holder.optionview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getAdapterPosition();
                        switch (position) {
                            case COUPON:
                                break;
                            case WALLET:
                                getActivity().startActivity(new Intent(getActivity(), UserWalletActivity.class));
                                break;
                            case SELL:
                                break;
                            case BUY:
                                break;
                            case SETTING:
                                getActivity().startActivity(new Intent(getActivity(), UserSettingActivity.class));
                                break;
                            case HELP:
                                break;
                            case LOGOFF:
                                showLogOffDialog();
                                break;
                        }
                    }
                });
                return holder;
            }

            @Override
            public void onBindViewHolder(UserOptionViewHolder holder, int position) {
                UserOptionClass optClass = options.get(position);
                holder.option.setText(optClass.getOption());
                Glide.with(mContext).load(optClass.getIconId()).into(holder.icon);
            }

            @Override
            public int getItemCount() {
                return options.size();
            }
        }

        public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

            private int space;

            public SpacesItemDecoration(int space) {
                this.space = space;
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;

                // Add top margin only for the first item to avoid double space between items
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = space;
                } else {
                    if (parent.getChildLayoutPosition(view) % 2 == 0)
                        outRect.top = 100;
                    else
                        outRect.top = 0;
                }
            }
        }

        ArrayList<UserOptionClass> optClasses;
        UserOptionAdpater adapter;
        private LoginInformationManager informationManager;
        private ImageView portrait;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(
                    R.layout.fragment_user_option_delete, container, false);
            initData();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.uinf_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new UserOptionAdpater(optClasses);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new SpacesItemDecoration(3));

            portrait = (ImageView) view.findViewById(R.id.user_portrait);
            informationManager = new LoginInformationManager(getActivity().getSharedPreferences("UserInfomation", MODE_PRIVATE));
            LinearLayout toUserInfo = (LinearLayout) view.findViewById(R.id.to_user_inf);
            initPortrait();
            portrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), UserPortraitActivity.class));
                    initPortrait();
                }
            });
            toUserInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), UserInformationActivity.class));
                }
            });

            return view;
        }

        private void initData() {
            optClasses = new ArrayList<UserOptionClass>();
            optClasses.add(new UserOptionClass(getResources().getString(R.string.uopt_mycoupon)));
            optClasses.add(new UserOptionClass(getResources().getString(R.string.uopt_mywallet)));
            optClasses.add(new UserOptionClass(getResources().getString(R.string.uopt_sell)));
            optClasses.add(new UserOptionClass(getResources().getString(R.string.uopt_buy)));
            optClasses.add(new UserOptionClass(getResources().getString(R.string.uopt_settings)));
            optClasses.add(new UserOptionClass(getResources().getString(R.string.uopt_help)));
            optClasses.add(new UserOptionClass(getResources().getString(R.string.uopt_logoff)));
        }

        public void initPortrait() {
            String s = informationManager.getPortraitPath();
            if (s != "") {
                Bitmap bitmap = BitmapFactory.decodeFile(s);
                portrait.setImageBitmap(bitmap);
            } else portrait.setImageResource(R.drawable.testportrait);
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
                                    new LoginInformationManager(getActivity().getSharedPreferences("UserInfomation", MODE_PRIVATE));
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
    }
    */

