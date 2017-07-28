package com.example.administrator.ccoupons.Connections;

import android.os.AsyncTask;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/7/28 0028.
 */

public class UploadTask extends AsyncTask<Void, Integer, String> {

    String url = DataHolder.base_URL + DataHolder.postAvatar_URL;
    String filepath, userId;
    public UploadTask(String userId, String path) {
        filepath = path;
        this.userId = userId;
    }
    @Override
    protected String doInBackground(Void... params) {
        uploadFile(filepath);
        return null;
    }
    protected void onPostExecute(String result) {

    }
    protected void onProgressUpdate(Integer... progress) {

    }

    // upload photos
    public void uploadFile(String uploadFile) {
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            MultipartEntity mpEntity = new MultipartEntity();
            File file = new File(uploadFile);
            ContentBody cbFile = new FileBody(file);
            mpEntity.addPart("userID", new StringBody(userId));
            mpEntity.addPart("imgFile", cbFile);

            httppost.setEntity(mpEntity);
            HttpResponse response = httpclient.execute(httppost);
            int statusCode=response.getStatusLine().getStatusCode();
            if(200 == statusCode){
                String result = EntityUtils.toString(response.getEntity());
                System.out.println(result);
            }
            httpclient.getConnectionManager().shutdown();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}