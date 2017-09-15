package com.example.administrator.ccoupons.Fragments.User;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/UserOptionFragment.java
import android.widget.ImageView;
import android.widget.LinearLayout;
=======
import android.widget.FrameLayout;
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/User/UserOptionFragment.java
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Main.WelcomeActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/UserOptionFragment.java
import com.example.administrator.ccoupons.User.UserBuyCoupons;
import com.example.administrator.ccoupons.User.UserFollowCoupon;
import com.example.administrator.ccoupons.User.UserSellCoupons;
import com.example.administrator.ccoupons.User.UserMyCouponActivity;
import com.example.administrator.ccoupons.User.UserPortraitActivity;
=======
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;
import com.example.administrator.ccoupons.UI.GlideCircleTransform;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.UserBuyCoupons;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.UserFollowCoupons;
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/User/UserOptionFragment.java
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
<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/UserOptionFragment.java
    private LoginInformationManager informationManager;
    private ImageView portrait;
=======


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
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/User/UserOptionFragment.java
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/UserOptionFragment.java
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_option, container, false);
        portrait = (ImageView) view.findViewById(R.id.user_portrait);
        TextView ub = (TextView) view.findViewById(R.id.user_ub);
        ub.setText(Integer.toString(DataHolder.User.UB));
        LinearLayout toUserMyCoupons = (LinearLayout) view.findViewById(R.id.user_to_mycoupons);
        LinearLayout toUserWal = (LinearLayout) view.findViewById(R.id.user_to_wal);
        LinearLayout toSetting = (LinearLayout) view.findViewById(R.id.user_to_set);
        LinearLayout logoff = (LinearLayout) view.findViewById(R.id.user_logoff);
        LinearLayout toUserSell = (LinearLayout) view.findViewById(R.id.user_sell);
        LinearLayout toUserBuy = (LinearLayout) view.findViewById(R.id.user_buy);
        LinearLayout toUserFollow = (LinearLayout) view.findViewById(R.id.user_follow);
        CollapsingToolbarLayout toUserInfo = (CollapsingToolbarLayout) view.findViewById(R.id.user_to_inf);
        mTitle = (TextView) view.findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) view.findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.main_appbar);
        toolbar = (Toolbar) view.findViewById(R.id.user_main_toolbar);


        informationManager = new LoginInformationManager(getActivity());
=======
    private int Ucoin;
    private String nickname;
    private String avatar_url;

    public void initViews() {
        userInfoManager = new UserInfoManager(getActivity());
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/User/UserOptionFragment.java
        mAppBarLayout.addOnOffsetChangedListener(this);
        initPortrait();

<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/UserOptionFragment.java
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
=======
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_option, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        initData();
        initPortrait();
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/User/UserOptionFragment.java
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
<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/UserOptionFragment.java
        String s = informationManager.getPortraitPath();
        if (s != "") {
            Bitmap bitmap = BitmapFactory.decodeFile(s);
            portrait.setImageBitmap(bitmap);
=======
        MyApp app = (MyApp) getActivity().getApplicationContext();
        String url = app.getAvatar();
        System.out.println("avatar = " + url);
        if (url != "") {
            Glide.with(getActivity())
                    .load(url)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(portrait);
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/User/UserOptionFragment.java
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static UserOptionFragment newInstance() {
        return new UserOptionFragment();
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

