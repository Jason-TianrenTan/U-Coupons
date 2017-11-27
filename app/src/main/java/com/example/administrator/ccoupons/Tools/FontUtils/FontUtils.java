package com.example.administrator.ccoupons.Tools.FontUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/2 0002.
 */

public class FontUtils {

    /**
     *
     * @param context
     * @param str
     * @return
     */
    public static Typeface getTypeface(Context context, String str) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), str);
        return typeface;
    }


    /**
     *
     * @param context
     * @param defaultFontNameToOverride
     * @param customFontFileNameInAssets
     */
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {

        final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Map<String, Typeface> newMap = new HashMap<String, Typeface>();
            newMap.put("serif", customFontTypeface);
            try {
                final Field staticField = Typeface.class
                        .getDeclaredField("sSystemFontMap");
                staticField.setAccessible(true);
                staticField.set(null, newMap);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
                defaultFontTypefaceField.setAccessible(true);
                defaultFontTypefaceField.set(null, customFontTypeface);
            } catch (Exception e) {
                System.out.println("Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
            }
        }
    }
}
