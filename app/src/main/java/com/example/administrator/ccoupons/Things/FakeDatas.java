package com.example.administrator.ccoupons.Things;

import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

/**
 * Created by CZJ on 2017/11/16.
 */

public class FakeDatas {
    public static ArrayList<Thing> getData(int cat) {
        ArrayList<Thing> arrayList = new ArrayList<Thing>();
        for (int i = 0; i < cat * 2 + 10; i++) {
            arrayList.add(new Thing(R.mipmap.huaji, "滑稽抱枕", "czj牌", cat, 99.99, 99));
        }
        return arrayList;
    }
}
