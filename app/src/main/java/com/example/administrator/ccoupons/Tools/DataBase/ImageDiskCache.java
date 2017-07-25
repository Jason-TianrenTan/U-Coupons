package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;


import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by CZJ on 2017/7/24.
 */

public class ImageDiskCache {
    private static ImageDiskCache imageDiskCache;
    DiskLruCache mDiskLruCache = null;

    private ImageDiskCache(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, "bitmap");
            //如果文件不存在，则创建
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageDiskCache getInstance(Context context) {
        if (imageDiskCache == null)
            imageDiskCache = new ImageDiskCache(context);
        return imageDiskCache;
    }

    //获取缓存地址
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (isExternalStorageWritable()) {
            cachePath = context.getExternalCacheDir().getPath();//如果挂载了sdcard，获取外部存储私有区域路径
        } else {
            cachePath = context.getCacheDir().getPath();//如果没有挂载sdcard，则获取内部存储缓存区域
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    //检查sd卡是否存在
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;//挂载了sdcard，返回真
        } else {
            return false;//否则返回假
        }
    }

    //版本控制
    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //传入url作为缓存文件的文件名
    public void writeImageToDiskCache(String imageUrl, Bitmap bitmap){
        try {
            String key = hashKeyForDisk(imageUrl);
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null && bitmap != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap readFromDiskCache(String imageUrl) {
        Bitmap bitmap = null;
        try {
            String key = hashKeyForDisk(imageUrl);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {//如果文件存在,读取数据转换为Bitmap对象
                InputStream is = snapShot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
