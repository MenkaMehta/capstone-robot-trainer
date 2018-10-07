package com.example.avatarmind.robotmotion;

/**
 * Created by menkamehta on 20/09/2018.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class PostTweetTask extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity postTActivity;
    private Twitter twitter;
    private Bitmap mBitmap;

    public PostTweetTask(Activity activity, Bitmap bitmap) {
        postTActivity = activity;
        mBitmap = bitmap;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("w0v1TbxAGA07m2jbwlCxgHDOu")
                .setOAuthConsumerSecret("0fqSWPf4hOLUkcn40U6GFLEEOBCbZnF9mmGoCmhaGGh3JLuxgX")
                .setOAuthAccessToken("1042591266188320768-uatdZLTcscb9XiFofmgbGqFhEeULG0")
                .setOAuthAccessTokenSecret("uRqn1x419vARe9S8MMQaqklMQ6mPg3uGSpG9PhvtnOFWQ");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(postTActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            File f = new File(postTActivity.getCacheDir(), "Menka's Image");
            f.createNewFile();

            //Convert bitmap to byte array
            Bitmap bitmap = mBitmap;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            StatusUpdate status = new StatusUpdate("API Robots");
            status.setMedia(f);
            twitter.updateStatus(status);
            //twitter4j.Status status = twitter.updateStatus("Robotic APIs");
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result) {
        statusDialog.dismiss();
    }

}

