package com.example.administrator.ccoupons.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.ImageView;

import com.example.administrator.ccoupons.Connections.ImageFetchr;
import com.example.administrator.ccoupons.Tools.DataBase.ImageDiskCache;
import com.example.administrator.ccoupons.Tools.DataBase.ImageLruCache;

/**
 * Created by CZJ on 2017/7/25.
 */

public class ImageManager {
    private static ImageDiskCache imageDiskCache;
    private static ImageLruCache imageLruCache;

    public static void GlideImage(String imgUrl, ImageView rootView, Context context) {
        imageDiskCache = ImageDiskCache.getInstance(context);
        imageLruCache = ImageLruCache.getInstance();
        Bitmap bitmap = imageLruCache.getFromMemoryCache(imgUrl);
        if (bitmap == null) {
            bitmap = imageDiskCache.readFromDiskCache(imgUrl);
        }
        if (bitmap == null) {
            //Todo:从服务器获取图片
            ImageFetchr fetchr = new ImageFetchr(imgUrl, rootView, imageDiskCache, imageLruCache);
            fetchr.execute();
        }
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        rootView.setImageDrawable(drawable);
        rootView.invalidate();
    }

    public static void GlideImage(String imgUrl, ImageView rootView) {
        imageLruCache = ImageLruCache.getInstance();
        Bitmap bitmap = imageLruCache.getFromMemoryCache(imgUrl);
        if (bitmap == null) {
            //Todo:从服务器获取图片
            System.out.println("从服务器获取图片.");
            ImageFetchr fetchr = new ImageFetchr(imgUrl, rootView);
            fetchr.execute();
        }
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        rootView.setImageDrawable(drawable);
        rootView.invalidate();
    }
}
