package com.example.administrator.ccoupons.User;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.R;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class CommonEmptyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.user_fragment_empty, container, false);
        return view;
    }
}
