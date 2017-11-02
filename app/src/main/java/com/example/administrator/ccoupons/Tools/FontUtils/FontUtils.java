package com.example.administrator.ccoupons.Tools.FontUtils;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/11/2 0002.
 */

public class FontUtils {

    public static Typeface getTypeface(Context context, String str) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), str);
        return typeface;
    }
}
