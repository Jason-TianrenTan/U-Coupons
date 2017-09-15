package com.example.administrator.ccoupons.Tools;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class PixelUtils {

    /**
     * transfer DP -> Pixels
     * @param context
     * @param dp to be converted
     * @return corresponding number in px
     */
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }


    /**
     * Reverse of dp2px
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
