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
import java.io.FileNotFoundException;
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
    ImageLruCache lruCache = ImageLruCache.getInstance();

    public ImageFetchr(String reqURL, ImageView parentView, ImageDiskCache diskCache) {
        this.imgView = parentView;
        this.url = reqURL;
        this.diskCache = diskCache;
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
        if(isCancelled()) return null;
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
        if(isCancelled()) return;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        System.out.println("download finished");
        BitmapDrawable drawable = new BitmapDrawable(result);
        imgView.setImageDrawable(drawable);
        imgView.invalidate();

        //Todo:test
        if (diskCache == null) {
            lruCache.addToMemoryCache(url, bitmap);
        } else {
            diskCache.writeToDiskCache(url, bitmap);
        }
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
            if (e instanceof FileNotFoundException) {
                System.out.println("Url Error: FileNotFoundException");
                this.cancel(true);
            }
            if (e instanceof NullPointerException){
                System.out.println("File Error: NullPointerException");
                this.cancel(true);
            }
        }
        return in;
    }
}
