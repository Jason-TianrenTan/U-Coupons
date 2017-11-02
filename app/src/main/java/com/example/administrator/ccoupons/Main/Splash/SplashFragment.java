package com.example.administrator.ccoupons.Main.Splash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.FontUtils.FontUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class SplashFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.splash_title)
    TextView splashTitle;
    @BindView(R.id.splash_content_above)
    TextView splashContentAbove;
    @BindView(R.id.splash_content_below)
    TextView splashContentBelow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.splash_view, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int res_id = bundle.getInt("res_id");
            String title = bundle.getString("title"), contentA = bundle.getString("content_above"),
                                                            contentB = bundle.getString("content_below");
            splashTitle.setText(title);
            splashTitle.setTypeface(FontUtils.getTypeface(getActivity(), "PingFang Regular.ttf"));
            splashContentAbove.setText(contentA);
            splashContentAbove.setTypeface(FontUtils.getTypeface(getActivity(), "PingFang Regular.ttf"));
            splashContentBelow.setText(contentB);
            splashContentBelow.setTypeface(FontUtils.getTypeface(getActivity(), "PingFang Regular.ttf"));
            ivSplash.setBackgroundResource(res_id);
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
