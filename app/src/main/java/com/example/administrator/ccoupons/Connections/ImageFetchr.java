package com.example.administrator.ccoupons.Connections;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;

import com.example.administrator.ccoupons.Tools.DataBase.ImageDiskCache;
import com.example.administrator.ccoupons.Tools.DataBase.ImageLruCache;
import com.example.administrator.ccoupons.Tools.ImageManager;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class ImageFetchr extends AsyncTask<String, Integer, Bitmap> {

    private ImageView imgView;
    private String url;
    private Bitmap bitmap;
    ImageDiskCache diskCache = null;
    ImageLruCache lruCache = null;

    public ImageFetchr(String reqURL, ImageView parentView, ImageDiskCache diskCache, ImageLruCache lruCache) {
        this.imgView = parentView;
        this.url = reqURL;
        this.diskCache = diskCache;
        this.lruCache = lruCache;
    }

    public ImageFetchr(String reqURL, ImageView parentView) {
        this.imgView = parentView;
        this.url = reqURL;
    }


    @Override
    protected void onPreExecute() {
        System.out.println("Loading " + url + ", preExecute");
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        System.out.println("Doing in background...");
        InputStream in = getInputStream(url);
        BufferedInputStream bis = new BufferedInputStream(in);
        bitmap = BitmapFactory.decodeStream(bis);

        try {
            in.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(Bitmap result) {
        System.out.println("download finished");
        BitmapDrawable drawable = new BitmapDrawable(result);
        imgView.setImageDrawable(drawable);
        imgView.invalidate();

        //Cache
        if (this.lruCache != null)
            lruCache.addToMemoryCache(url, bitmap);
        if (this.diskCache != null)
            diskCache.writeToDiskCache(url, bitmap);
        System.out.println("Cache url  = " + url);
    }

    private InputStream getInputStream(String url_str) {
        InputStream in = null;
        try {
            URL url = new URL(url_str);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5 * 1000);
            in = connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return in;
    }

}
