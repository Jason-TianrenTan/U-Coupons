package com.example.administrator.ccoupons.Tools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

/**
 * Created by CZJ on 2017/7/18.
 */

public class TakePhotoUtil implements TakePhoto.TakeResultListener, InvokeListener {
    private static final String TAG = TakePhotoUtil.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Activity activity;

    public TakePhotoUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * get TakePhoto Instance
     *
     * @return the TakePhoto instance
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(activity, this));
        }
        return takePhoto;
    }

    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(activity, type, invokeParam, this);
    }

    /**
     * @param result
     */
    @Override
    public void takeSuccess(TResult result) {
        if (listener != null) {
            listener.takeSuccess(result);
        }
//        deleteCachePic();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        if (listener != null) {
            listener.takeFail(result, msg);
        }
//        deleteCachePic();
    }

    @Override
    public void takeCancel() {
        if (listener != null) {
            listener.takeCancel();
        }
    }

    public void deleteCachePic() {
        File file = new File(Environment.getExternalStorageDirectory(), "/takephoto/");
        if (!file.exists()) return;
        File[] files = file.listFiles();
        for (File f : files) {
            f.delete();
        }
    }

    public interface TakePhotoListener {
        void takeSuccess(TResult result);

        void takeFail(TResult result, String msg);

        void takeCancel();
    }

    public TakePhotoListener listener;

    public void setTakePhotoListener(SimpleTakePhotoListener listener) {
        this.listener = listener;
    }

    public static class SimpleTakePhotoListener implements TakePhotoListener {
        @Override
        public void takeSuccess(TResult result) {
        }

        @Override
        public void takeFail(TResult result, String msg) {
        }

        @Override
        public void takeCancel() {
        }
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(activity), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * @param select_type
     */
    public void takePhoto(Select_type select_type, SimpleTakePhotoListener listener) {
        takePhoto(select_type, null, listener);
    }

    public void takePhoto(Select_type select_type, PhotoConfigOptions cropOptions, SimpleTakePhotoListener listener) {
        if (takePhoto == null) {
            Toast.makeText(activity, "请先开启照片功能", Toast.LENGTH_SHORT).show();
            return;
        }
        setTakePhotoListener(listener);
        if (cropOptions == null) {
            cropOptions = new PhotoConfigOptions();
        }
        cropOptions.configCompress();   //压缩配置
        cropOptions.configTakePhoto();  //拍照配置
        File file = new File(Environment.getExternalStorageDirectory(), "/takephoto/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        switch (select_type) {
            case PICK_BY_SELECT:  //从相册获取
                if (cropOptions.limit > 1) {
                    if (cropOptions.crop == true) {
                        takePhoto.onPickMultipleWithCrop(cropOptions.limit, cropOptions.getCropOptions());
                    } else {
                        takePhoto.onPickMultiple(cropOptions.limit);
                    }
                }
                if (cropOptions.chooseFromFile) {
                    if (cropOptions.crop == true) {
                        takePhoto.onPickFromDocumentsWithCrop(imageUri, cropOptions.getCropOptions());
                    } else {
                        takePhoto.onPickFromDocuments();
                    }
                } else {
                    if (cropOptions.crop == true) {
                        takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions.getCropOptions());
                    } else {
                        takePhoto.onPickFromGallery();
                    }
                }
                break;
            case PICK_BY_TAKE:  //拍照获取
                if (cropOptions.crop == true) {
                    takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions.getCropOptions());
                } else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                break;
            case PICK_BY_SELECT_NOT_CROP:
                takePhoto.onPickFromGallery();
                break;
            case PICK_BY_TAKE_NOT_CROP:
                takePhoto.onPickFromCapture(imageUri);
                break;
            default:
                break;
        }
    }

    /**
     * the config of conpress
     */
    public class PhotoConfigOptions {
        //裁剪配置
        private boolean crop = true;  //是否裁剪
        private boolean withWonCrop = false;  //是否采用自带的裁剪工具，默认选取第三方的裁剪工具
        private boolean cropSize = true; //尺寸还是比例
        //压缩配置
        private boolean useOwnCompressTool = true;  //使用自带的压缩工具
        private boolean isCompress = true;  //是否压缩
        private boolean showProgressBar = true; //显示压缩进度条
        //        private
        private int maxSize = 102400;
        //选择图片配置
        private boolean useOwnGallery = false; //选择使用自带的相册
        private boolean chooseFromFile = false;  //从文件获取图片
        private int limit = 1;  //选择最多图片的配置，选择多张图片会自动切换到TakePhoto自带相册
        //其它配置
        private boolean savePic = true;  //选择完之后是否保存图片
        private boolean correctTool = false; //纠正拍照的照片旋转角度
        private int height = 200;
        private int width = 200;

        /**
         * the config of crop
         *
         * @return
         */
        public CropOptions getCropOptions() {
            if (crop == false) return null;
            CropOptions.Builder builder = new CropOptions.Builder();
            if (cropSize) {
                builder.setOutputX(width).setOutputY(height);
            } else {
                builder.setAspectX(width).setAspectY(height);
            }
            builder.setWithOwnCrop(withWonCrop);  //默认采用第三方配置
            return builder.create();
        }

        /**
         * the config of conpress
         */
        public void configCompress() {
            if (isCompress == false) {
                takePhoto.onEnableCompress(null, false);
                return;
            }
            CompressConfig config;
            if (useOwnCompressTool) {
                config = new CompressConfig.Builder()
                        .setMaxSize(maxSize)
                        .setMaxPixel(width > height ? width : height)
                        .enableReserveRaw(savePic)
                        .create();
            } else {
                LubanOptions options = new LubanOptions.Builder()
                        .setMaxHeight(height)
                        .setMaxWidth(maxSize)
                        .create();
                config = CompressConfig.ofLuban(options);
                config.enableReserveRaw(savePic);
            }
            takePhoto.onEnableCompress(config, showProgressBar);
        }

        public void configTakePhoto() {
            TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
            if (useOwnGallery) {
                builder.setWithOwnGallery(true);
            }
            if (correctTool) {
                builder.setCorrectImage(true);
            }
            takePhoto.setTakePhotoOptions(builder.create());
        }

        public void setCrop(boolean crop) {
            this.crop = crop;
        }

        public void setWithWonCrop(boolean withWonCrop) {
            this.withWonCrop = withWonCrop;
        }

        public void setCropSize(boolean cropSize) {
            this.cropSize = cropSize;
        }

        public void setUseOwnCompressTool(boolean useOwnCompressTool) {
            this.useOwnCompressTool = useOwnCompressTool;
        }

        public void setCompress(boolean compress) {
            isCompress = compress;
        }

        public void setShowProgressBar(boolean showProgressBar) {
            this.showProgressBar = showProgressBar;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public void setUseOwnGallery(boolean useOwnGallery) {
            this.useOwnGallery = useOwnGallery;
        }

        public void setChooseFromFile(boolean chooseFromFile) {
            this.chooseFromFile = chooseFromFile;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public void setSavePic(boolean savePic) {
            this.savePic = savePic;
        }

        public void setCorrectTool(boolean correctTool) {
            this.correctTool = correctTool;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

    /**
     * the way to get picture
     */
    public enum Select_type {
        PICK_BY_SELECT, PICK_BY_TAKE, PICK_BY_SELECT_NOT_CROP, PICK_BY_TAKE_NOT_CROP
    }
}