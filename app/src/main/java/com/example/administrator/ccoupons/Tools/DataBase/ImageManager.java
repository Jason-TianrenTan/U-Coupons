package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.administrator.ccoupons.Tools.DataBase.ImageDiskCache;
import com.example.administrator.ccoupons.Tools.DataBase.ImageLruCache;

/**
 * Created by CZJ on 2017/7/25.
 */

public class ImageManager {
    private ImageDiskCache imageDiskCache;
    private ImageLruCache imageLruCache;

    public ImageManager(Context context) {
        imageDiskCache = ImageDiskCache.getInstance(context);
        imageLruCache = ImageLruCache.getInstance();
    }

    public void saveToMemory(String imgUrl, Bitmap bitmap) {
        imageLruCache.addToMemoryCache(imgUrl, bitmap);
    }

    public void saveToDisk(String imgUrl, Bitmap bitmap) {
        imageDiskCache.writeImageToDiskCache(imgUrl, bitmap);
    }

    public Bitmap getImage(String imgUrl) {
        Bitmap bitmap = imageLruCache.getFromMemoryCache(imgUrl);
        if (bitmap == null) {
            bitmap = imageDiskCache.readFromDiskCache(imgUrl);
        }
        if (bitmap == null) {
            //Todo:从服务器获取图片
        }
        return bitmap;
    }
}
