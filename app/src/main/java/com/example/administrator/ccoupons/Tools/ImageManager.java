package com.example.administrator.ccoupons.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.administrator.ccoupons.Connections.ImageFetchr;
import com.example.administrator.ccoupons.Tools.DataBase.ImageDiskCache;
import com.example.administrator.ccoupons.Tools.DataBase.ImageLruCache;

import java.io.File;

/**
 * Created by CZJ on 2017/7/25.
 */
public class ImageManager {
    private static ImageDiskCache imageDiskCache;
    private static ImageLruCache imageLruCache = ImageLruCache.getInstance();

    //三级缓存
    public static void GlideImage(String imgUrl, ImageView rootView, Context context) {
        imageDiskCache = ImageDiskCache.getInstance(context);
        Bitmap bitmap = imageLruCache.getFromMemoryCache(imgUrl);
        //     System.out.println("从内存获取");
        if (bitmap == null) {
            bitmap = imageDiskCache.readFromDiskCache(imgUrl);
            //    System.out.println("从本地获取");
        }
        if (bitmap == null) {
            //Todo:从服务器获取图片
            ImageFetchr fetchr = new ImageFetchr(imgUrl, rootView, imageDiskCache);
            //   System.out.println("从服务器获取");
            fetchr.execute();
        } else {
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            rootView.setImageDrawable(drawable);
            rootView.invalidate();
        }
    }

    //二级缓存
    public static void GlideImage(String imgUrl, ImageView rootView) {
        Bitmap bitmap = imageLruCache.getFromMemoryCache(imgUrl);
        //      System.out.println("从内存获取");
        if (bitmap == null) {
            //Todo:从服务器获取图片
            //          System.out.println("从服务器获取");
            ImageFetchr fetchr = new ImageFetchr(imgUrl, rootView);
            fetchr.execute();
        } else {
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            rootView.setImageDrawable(drawable);
            rootView.invalidate();
        }
    }
    //上传图片
}

