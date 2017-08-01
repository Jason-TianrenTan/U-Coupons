package com.example.administrator.ccoupons.Banner;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context,int position, String data) {
        imageView.setImageResource(R.drawable.mascot_nothing);
        ImageLoader.getInstance().displayImage(data,imageView);
    }
}
