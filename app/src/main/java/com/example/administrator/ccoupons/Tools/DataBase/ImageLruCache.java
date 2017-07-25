package com.example.administrator.ccoupons.Tools.DataBase;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by CZJ on 2017/7/24.
 */

public class ImageLruCache {
    private LruCache<String, Bitmap> stringBitmapLruCache;
    int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取最大内存
    int cacheSize = maxMemory / 8;//大小为最大内存的1/8
    private static ImageLruCache imageLruCache;

    /**
     * 私有化构造方法
     */
    private ImageLruCache() {
        stringBitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 单例模式获取实例，保证只有一个CustomLruCache对象，同时保证只有一个CustomLruCache.stringBitmapLruCache
     *
     * @return
     */
    public static ImageLruCache getInstance() {
        if (imageLruCache == null) {
            imageLruCache = new ImageLruCache();
        }
        return imageLruCache;
    }

    public void addToMemoryCache(String key, Bitmap bitmap) {
        if (getFromMemoryCache(key) != bitmap)//如果缓存中不存在bitmap,就存入缓存
            stringBitmapLruCache.put(key, bitmap);
    }

    public Bitmap getFromMemoryCache(String key) {
        return stringBitmapLruCache.get(key);
    }
}
