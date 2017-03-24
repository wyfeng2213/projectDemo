package com.cmcc.patient.im;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.patient.R;
import com.justalk.cloud.lemon.MtcApi;
import com.justalk.cloud.lemon.MtcIm;
import com.justalk.cloud.lemon.MtcImConstants;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;


public class OriginalActivity extends Activity {

    public static final String ORIGINAL_PATH = "original_path";
    public static final String ORIGINAL_URI = "original_uri";
    private static final int ORIGINAL_COOKIE = -100;
    private ProgressBar progressBar;
    private ImageView originalImage;
    private TextView status;
    private Bitmap bitmap;

    private BroadcastReceiver mtcImFetchingReceiver;
    private BroadcastReceiver mtcImFetchOkReceiver;
    private BroadcastReceiver mtcImFetchDidFailReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original);
        registerReceivers();
        progressBar = (ProgressBar) findViewById(R.id.pb_original_progress);
        status = (TextView) findViewById(R.id.tv_status);
        originalImage = (ImageView) findViewById(R.id.iv_original_image);
        originalImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        String originalUri = getIntent().getStringExtra(OriginalActivity.ORIGINAL_URI);
        String originalPath = getIntent().getStringExtra(OriginalActivity.ORIGINAL_PATH);
        if (originalPath.toLowerCase().endsWith(".jpg") || originalPath.toLowerCase().endsWith(".png")) {
            bitmap = BitmapFactory.decodeFile(originalPath);
            if (bitmap == null)
                fetchFile(originalUri, originalPath);
            else {
                originalImage.setImageBitmap(bitmap);
                status.setText("file fetch successfully!");
            }

        } else {
            File file = new File(originalPath);
            if (file.exists()) {
                status.setText("file fetch successfully!");
                playVideo(originalPath);
            } else
                fetchFile(originalUri, originalPath);
        }

    }

    private void fetchFile(String uri, String path) {
        originalImage.setImageResource(R.drawable.ic_launcher);
        progressBar.setVisibility(View.VISIBLE);
        MtcIm.Mtc_ImFetchFile(ORIGINAL_COOKIE, uri, path);
    }

    private void playVideo(String path) {
        originalImage.setImageResource(R.drawable.ic_launcher);
        if (path.toLowerCase().endsWith(".mp4") ||
                path.toLowerCase().endsWith(".3gp") ||
                path.toLowerCase().endsWith(".avi") ||
                path.toLowerCase().endsWith(".wmv")) {

            Intent it = new Intent(Intent.ACTION_VIEW);
            it.setDataAndType(Uri.parse(path), "video/*");
            startActivity(it);
        } else {
            Toast.makeText(this, "Do not support!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        unregisterReceivers();
        super.onDestroy();
    }

    private void registerReceivers() {
        mtcImFetchingReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                try {
                    JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                    int progress = json.getInt(MtcImConstants.MtcImProgressKey);
                    progressBar.setProgress(progress);
                    status.setText("file fetching...");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        mtcImFetchOkReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                int cookie = intent.getIntExtra(MtcApi.EXTRA_COOKIE, -100);
                if (cookie == ORIGINAL_COOKIE) {
                    try {
                        String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                        JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                        String fileName = json.getString(MtcImConstants.MtcImFilePathKey);
                        progressBar.setVisibility(View.GONE);
                        status.setText("file fetch successfully!");
                        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".png")) {
                            bitmap = BitmapFactory.decodeFile(fileName);
                            originalImage.setImageBitmap(bitmap);
                        } else {
                            playVideo(fileName);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };

        mtcImFetchDidFailReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                int cookie = intent.getIntExtra(MtcApi.EXTRA_COOKIE, -100);
                if (cookie == ORIGINAL_COOKIE) {
                    status.setText("file fetch fail!");
                }
            }
        };

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(mtcImFetchingReceiver, new IntentFilter(MtcImConstants.MtcImFetchingNotification));
        broadcastManager.registerReceiver(mtcImFetchOkReceiver, new IntentFilter(MtcImConstants.MtcImFetchOkNotification));
        broadcastManager.registerReceiver(mtcImFetchDidFailReceiver, new IntentFilter(MtcImConstants.MtcImFetchDidFailNotification));
    }

    private void unregisterReceivers() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(mtcImFetchingReceiver);
        broadcastManager.unregisterReceiver(mtcImFetchOkReceiver);
        broadcastManager.unregisterReceiver(mtcImFetchDidFailReceiver);
        mtcImFetchingReceiver = null;
        mtcImFetchOkReceiver = null;
        mtcImFetchDidFailReceiver = null;
    }
}
