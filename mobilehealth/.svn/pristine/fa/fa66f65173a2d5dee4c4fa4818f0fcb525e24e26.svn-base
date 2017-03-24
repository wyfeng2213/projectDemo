package com.cmcc.patient.im;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.cmcc.patient.R;
import com.justalk.cloud.lemon.MtcApi;
import com.justalk.cloud.lemon.MtcConstants;
import com.justalk.cloud.lemon.MtcIm;
import com.justalk.cloud.lemon.MtcImConstants;
import com.justalk.cloud.lemon.MtcUeDb;
import com.justalk.cloud.lemon.MtcUser;
import com.justalk.cloud.lemon.MtcUserConstants;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ChatActivity extends Activity {

    private EditText editTextUsername;
    private EditText editTextUserContent;
    private ListView listViewChat;
    private List<MessageModel> dataSource;
    private ChatListViewAdapter adapter;
    private int fileType;

    private BroadcastReceiver mtcImTextDidReceiveReceiver;
    private BroadcastReceiver mtcImInfoDidReceiveReceiver;
    private BroadcastReceiver mtcImFileDidReceiveReceiver;
    private BroadcastReceiver mtcImSendingReceiver;
    private BroadcastReceiver mtcImSendOkReceiver;
    private BroadcastReceiver mtcImSendDidFailReceiver;
    private BroadcastReceiver mtcImFetchingReceiver;
    private BroadcastReceiver mtcImFetchOkReceiver;
    private BroadcastReceiver mtcImFetchDidFailReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        configData();
        configUi();
        registerReceivers();
        MtcIm.Mtc_ImRefresh();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        unregisterReceivers();
        super.onDestroy();
    }

    private void configData() {
        dataSource = new ArrayList<MessageModel>();
    }

    private void configUi() {
        editTextUsername = (EditText) findViewById(R.id.et_chat_username);
        editTextUserContent = (EditText) findViewById(R.id.et_chat_text);
        listViewChat = (ListView) findViewById(R.id.lv_chat_records);
        adapter = new ChatListViewAdapter(this);
        adapter.setDatasource(dataSource);
        listViewChat.setAdapter(adapter);
    }

    public void onSend(View view) {
        String text = editTextUserContent.getText().toString();
        String username = editTextUsername.getText().toString();
        if (text.length() > 0 && username.length() > 0) {
            MessageModel model = new MessageModel();
            model.setMsgText(text);
            model.setMsgByUser("To " + username + ":");
            model.setMsgUiType(ChatListViewAdapter.TYPE_TEXT_RIGHT);
            dataSource.add(model);
            adapter.setDatasource(dataSource);
            adapter.notifyDataSetChanged();
            listViewChat.setSelection(dataSource.size());
            editTextUserContent.setText("");

            String info = String.format(Locale.getDefault(), "{\"MtcImDisplayNameKey\":\"%s\"}", MtcUeDb.Mtc_UeDbGetUserName());
            String userUri = MtcUser.Mtc_UserFormUri(MtcUserConstants.EN_MTC_USER_ID_USERNAME, username);
            int cookie = dataSource.indexOf(model);
            int ret = MtcIm.Mtc_ImSendText(cookie, userUri, text, info);
            if (ret != MtcConstants.ZOK) {
                model.setMsgStatus("send fail!");
                adapter.setDatasource(dataSource);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void onSendLocation(View view) {
        if (Build.VERSION.SDK_INT > 22 && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            return;
        }

        String username = editTextUsername.getText().toString();
        if (username != null && username.length() > 0) {
            String userUri = MtcUser.Mtc_UserFormUri(
                    MtcUserConstants.EN_MTC_USER_ID_USERNAME, username);
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            String locationProvider = "";
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            }

            Location location = locationManager
                    .getLastKnownLocation(locationProvider);
            String locationStr = "";
            if (location != null) {
                locationStr = "Latitude：" + location.getLatitude() + "\n"
                        + "Longitude：" + location.getLongitude();
            } else {
                locationStr = "Latitude：" + 29.879337 + "\n" + "Longitude："
                        + 121.630172;
            }
            MessageModel model = new MessageModel();
            model.setMsgText("Location" + "\n" + locationStr);
            model.setMsgByUser("To " + username + ":");
            model.setMsgUiType(ChatListViewAdapter.TYPE_TEXT_RIGHT);
            dataSource.add(model);
            adapter.setDatasource(dataSource);
            adapter.notifyDataSetChanged();
            listViewChat.setSelection(dataSource.size());
            String info = String.format(Locale.getDefault(), "{\"MtcImDisplayNameKey\":\"%s\"}", MtcUeDb.Mtc_UeDbGetUserName());
            int ret = MtcIm.Mtc_ImSendInfo(0, userUri, "Location", locationStr, info);
            if (ret != MtcConstants.ZOK) {
                model.setMsgStatus("send fail!");
                adapter.setDatasource(dataSource);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void onSendFile(View view) {
        final CharSequence[] items = {"Photo", "Video", "Clean Cache"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose File");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if (item == 0) {
                    Intent getAlbum = new Intent();
                    getAlbum.setType("image/*");
                    getAlbum.setAction(Intent.ACTION_PICK);
                    startActivityForResult(getAlbum, 1);
                    fileType = 0;
                } else if (item == 1) {
                    Intent getAlbum = new Intent();
                    getAlbum.setType("video/*");
                    getAlbum.setAction(Intent.ACTION_PICK);
                    startActivityForResult(getAlbum, 1);
                    fileType = 1;
                } else {
                    cleanData(FileUtils.getSendDir(getApplicationContext()));
                    cleanData(FileUtils.getRecvDir(getApplicationContext()));
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void cleanData(String filePath) {
        File directory = new File(filePath);
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                file.delete();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            String sendThumbPath = "";
            Uri uri = data.getData();
            ContentResolver resolver = this.getContentResolver();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = resolver.query(uri, filePathColumn, null, null,
                    null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String resPath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = null;
            if (resPath.toLowerCase().endsWith(".jpg") || resPath.toLowerCase().endsWith(".png"))
                bmp = getImageThumb(resPath);
            else
                bmp = getVideoThumb(resPath);

            if ((resPath != null) && (resPath.length() > 0)) {
                int dot = resPath.lastIndexOf("/");
                int endDot = resPath.lastIndexOf(".");
                if ((dot > -1) && (endDot < (resPath.length() - 1))) {
                    sendThumbPath = FileUtils.getSendDir(getApplicationContext()) + resPath.substring(dot + 1, endDot) + ".jpg";
                }
            }

            try {
                File myCaptureFile = new File(sendThumbPath);
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(myCaptureFile));
                bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            sendFile(sendThumbPath, resPath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap getImageThumb(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = computeSampleSize(options, -1, 256 * 256);
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
        return bmp;
    }

    private Bitmap getVideoThumb(String videoPath) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MICRO_KIND);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 196, 256,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    private void sendFile(String thumbPath, String path) {
        String username = editTextUsername.getText().toString();
        if (username != null && username.length() > 0) {
            String userUri = MtcUser.Mtc_UserFormUri(
                    MtcUserConstants.EN_MTC_USER_ID_USERNAME, username);

            MessageModel model = new MessageModel();
            model.setMsgThumbPath(thumbPath);
            model.setMsgOriginalPath(path);
            model.setMsgByUser("To " + username + ":");
            model.setMsgUiType(ChatListViewAdapter.TYPE_IMAGE_RIGHT);
            dataSource.add(model);
            adapter.setDatasource(dataSource);
            adapter.notifyDataSetChanged();
            listViewChat.setSelection(dataSource.size());
            String sendThumbPath = String.format(Locale.getDefault(), "{\"MtcImThumbFilePathKey\":\"%s\", \"MtcImDisplayNameKey\":\"%s\"}", thumbPath, MtcUeDb.Mtc_UeDbGetUserName());
            int cookie = dataSource.indexOf(model);
            int type = -1;
            if (fileType == 0)
                type = MtcImConstants.EN_MTC_IM_FILE_IMAGE;
            else if (fileType == 1)
                type = MtcImConstants.EN_MTC_IM_FILE_VIDEO;
            int ret = MtcIm.Mtc_ImSendFile(cookie, userUri, type, path, sendThumbPath);
            if (ret != MtcConstants.ZOK) {
                model.setMsgStatus("send fail!");
                adapter.setDatasource(dataSource);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void imTextDidReceive(MessageModel model) {
        dataSource.add(model);
        adapter.setDatasource(dataSource);
        adapter.notifyDataSetChanged();
        listViewChat.setSelection(dataSource.size());
    }

    private void imSendOk(int cookie) {
        MessageModel model = dataSource.get(cookie);
        model.setMsgStatus("send successfully!");
        adapter.setDatasource(dataSource);
        adapter.notifyDataSetChanged();
        listViewChat.setSelection(dataSource.size());
    }

    private void imSendFail(int cookie) {
        MessageModel model = dataSource.get(cookie);
        model.setMsgStatus("send fail!");
        adapter.setDatasource(dataSource);
        adapter.notifyDataSetChanged();
        listViewChat.setSelection(dataSource.size());
    }

    private void registerReceivers() {

        mtcImTextDidReceiveReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                MessageModel model = new MessageModel();
                try {
                    String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                    JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                    String userUri = json.getString(MtcImConstants.MtcImUserUriKey);
                    String username = MtcUser.Mtc_UserGetId(userUri);
                    model.setMsgByUser("From " + username + ":");
                    model.setMsgText(json.getString(MtcImConstants.MtcImTextKey));
                    model.setMsgUiType(ChatListViewAdapter.TYPE_TEXT_LEFT);
                    model.setMsgStatus("received!");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                imTextDidReceive(model);
            }
        };

        mtcImInfoDidReceiveReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                MessageModel model = new MessageModel();
                try {
                    String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                    JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                    String userUri = json.getString(MtcImConstants.MtcImUserUriKey);
                    String username = MtcUser.Mtc_UserGetId(userUri);
                    model.setMsgByUser("From " + username + ":");
                    String infoType = json.getString(MtcImConstants.MtcImInfoTypeKey);
                    model.setMsgText(infoType + "\n" + json.getString(MtcImConstants.MtcImInfoContentKey));
                    model.setMsgUiType(ChatListViewAdapter.TYPE_TEXT_LEFT);
                    model.setMsgStatus("received!");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                imTextDidReceive(model);
            }
        };

        mtcImFileDidReceiveReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                MessageModel model = new MessageModel();
                try {
                    String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                    JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                    String userUri = json.getString(MtcImConstants.MtcImUserUriKey);
                    String username = MtcUser.Mtc_UserGetId(userUri);
                    model.setMsgByUser("From " + username + ":");
                    model.setMsgUiType(ChatListViewAdapter.TYPE_IMAGE_LEFT);
                    model.setMsgStatus("received!");

                    String fileUri = json.getString(MtcImConstants.MtcImFileUriKey);
                    String fileName = json.getString(MtcImConstants.MtcImFileNameKey);
                    String path = FileUtils.getRecvDir(getApplicationContext()) + fileName;
                    model.setMsgOriginalUri(fileUri);
                    model.setMsgOriginalPath(path);
                    dataSource.add(model);

                    String thumbUri = json.optString(MtcImConstants.MtcImThumbFileUriKey, "");
                    if (thumbUri != null && thumbUri.length() > 0) {
                        int endDot = fileName.lastIndexOf(".");
                        String thumbPath = FileUtils.getRecvDir(getApplicationContext()) + "Thumb" + fileName.substring(1, endDot) + ".jpg";
                        int cookie = dataSource.indexOf(model);
                        int ret = MtcIm.Mtc_ImFetchFile(cookie, thumbUri, thumbPath);
                        if (ret != MtcConstants.ZOK) {
                            model.setMsgStatus("fetch fail!");
                        }
                    } else {
                        model.setMsgStatus("no thumb!");
                    }

                    adapter.setDatasource(dataSource);
                    adapter.notifyDataSetChanged();
                    listViewChat.setSelection(dataSource.size());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        mtcImSendingReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                int cookie = intent.getIntExtra(MtcApi.EXTRA_COOKIE, -1);
                MessageModel model = dataSource.get(cookie);
                try {
                    JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                    int progress = json.getInt(MtcImConstants.MtcImProgressKey);
                    model.setProgress(progress);
                    model.setMsgStatus("sending...");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                adapter.setDatasource(dataSource);
                adapter.notifyDataSetChanged();
                listViewChat.setSelection(dataSource.size());
            }
        };

        mtcImSendOkReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                int cookie = intent.getIntExtra(MtcApi.EXTRA_COOKIE, -1);
                imSendOk(cookie);
            }
        };

        mtcImSendDidFailReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                int cookie = intent.getIntExtra(MtcApi.EXTRA_COOKIE, -1);
                imSendFail(cookie);
            }
        };

        mtcImFetchingReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                int cookie = intent.getIntExtra(MtcApi.EXTRA_COOKIE, -1);
                if (cookie > -1) {
                    MessageModel model = dataSource.get(cookie);
                    try {
                        JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                        int progress = json.getInt(MtcImConstants.MtcImProgressKey);
                        model.setProgress(progress);
                        model.setMsgStatus("thumb fetching...");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    adapter.setDatasource(dataSource);
                    adapter.notifyDataSetChanged();
                    listViewChat.setSelection(dataSource.size());
                }
            }
        };

        mtcImFetchOkReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                int cookie = intent.getIntExtra(MtcApi.EXTRA_COOKIE, -1);
                if (cookie > -1) {
                    try {
                        String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                        JSONObject json = (JSONObject) new JSONTokener(info)
                                .nextValue();
                        String fileName = json
                                .getString(MtcImConstants.MtcImFilePathKey);
                        MessageModel model = dataSource.get(cookie);
                        model.setMsgThumbPath(fileName);
                        model.setMsgStatus("thumb fetch successfully!");
                        adapter.setDatasource(dataSource);
                        adapter.notifyDataSetChanged();
                        listViewChat.setSelection(dataSource.size());
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
                int cookie = intent.getIntExtra(MtcApi.EXTRA_COOKIE, -1);
                if (cookie > -1) {
                    MessageModel model = dataSource.get(cookie);
                    model.setMsgStatus("thumb fetch fail!");
                    adapter.setDatasource(dataSource);
                    adapter.notifyDataSetChanged();
                    listViewChat.setSelection(dataSource.size());
                }
            }
        };

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(mtcImTextDidReceiveReceiver, new IntentFilter(MtcImConstants.MtcImTextDidReceiveNotification));
        broadcastManager.registerReceiver(mtcImInfoDidReceiveReceiver, new IntentFilter(MtcImConstants.MtcImInfoDidReceiveNotification));
        broadcastManager.registerReceiver(mtcImFileDidReceiveReceiver, new IntentFilter(MtcImConstants.MtcImFileDidReceiveNotification));
        broadcastManager.registerReceiver(mtcImSendingReceiver, new IntentFilter(MtcImConstants.MtcImSendingNotification));
        broadcastManager.registerReceiver(mtcImSendOkReceiver, new IntentFilter(MtcImConstants.MtcImSendOkNotification));
        broadcastManager.registerReceiver(mtcImSendDidFailReceiver, new IntentFilter(MtcImConstants.MtcImSendDidFailNotification));
        broadcastManager.registerReceiver(mtcImFetchingReceiver, new IntentFilter(MtcImConstants.MtcImFetchingNotification));
        broadcastManager.registerReceiver(mtcImFetchOkReceiver, new IntentFilter(MtcImConstants.MtcImFetchOkNotification));
        broadcastManager.registerReceiver(mtcImFetchDidFailReceiver, new IntentFilter(MtcImConstants.MtcImFetchDidFailNotification));
    }

    private void unregisterReceivers() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(mtcImTextDidReceiveReceiver);
        broadcastManager.unregisterReceiver(mtcImInfoDidReceiveReceiver);
        broadcastManager.unregisterReceiver(mtcImFileDidReceiveReceiver);
        broadcastManager.unregisterReceiver(mtcImSendingReceiver);
        broadcastManager.unregisterReceiver(mtcImSendOkReceiver);
        broadcastManager.unregisterReceiver(mtcImSendDidFailReceiver);
        broadcastManager.unregisterReceiver(mtcImFetchingReceiver);
        broadcastManager.unregisterReceiver(mtcImFetchOkReceiver);
        broadcastManager.unregisterReceiver(mtcImFetchDidFailReceiver);
        mtcImTextDidReceiveReceiver = null;
        mtcImInfoDidReceiveReceiver = null;
        mtcImFileDidReceiveReceiver = null;
        mtcImSendingReceiver = null;
        mtcImSendOkReceiver = null;
        mtcImSendDidFailReceiver = null;
        mtcImFetchingReceiver = null;
        mtcImFetchOkReceiver = null;
        mtcImFetchDidFailReceiver = null;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
