package com.cmcc.mobilehealthcare.application;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.cmcc.mobilehealthcare.R;
import com.cmcc.requestlibrary.utils.NetConfig;
import com.justalk.cloud.juscall.JusCallConfig;
import com.justalk.cloud.juscall.MtcCallDelegate;
import com.justalk.cloud.jusdoodle.DoodleDelegate;
import com.justalk.cloud.juslogin.LoginDelegate;
import com.justalk.cloud.juspush.MiPush;

import java.io.IOException;
import java.io.InputStream;

import static com.justalk.cloud.juslogin.LoginDelegate.InitStat.MTC_INIT_SUCCESS;

/**
 * @author shuyang on 2016/10/24.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // 添加https证书
        try {
//            String[]  certFiles=this.getAssets().list("certs");
//            if (certFiles != null) {
//                for (String cert:certFiles) {
//                    InputStream is = getAssets().open("certs/" + cert);
//                    NetConfig.addCertificate(is); // 这里将证书读取出来，，放在配置中byte[]里
//                }
//            }
            InputStream is=this.getAssets().open("kuandai10086.crt");
            NetConfig.addCertificate(is);
            if (Build.VERSION.SDK_INT > 22 && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) return;

            if (LoginDelegate.init(this, getString(R.string.JusTalkCloud_AppKey)) != MTC_INIT_SUCCESS) return;
            MtcCallDelegate.init(this);
            JusCallConfig.setBackIntentAction("com.justalk.cloud.sample.call.action.backfromcall");
            MiPush.setCallPushParm();
            DoodleDelegate.init(this);
            String[] imgs = new String[] {"/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_0.jpg",
                    "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_1.jpg",
                    "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_2.jpg",
                    "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_3.jpg",
                    "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_4.jpg",
                    "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_5.jpg",
                    "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_6.jpg",
                    "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_7.jpg",
                    "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_8.jpg"};
            DoodleDelegate.setBackgroundImages(imgs);
            MiPush.setImTextPushParm("${Sender}", "${Text}");
            MiPush.setImImagePushParm("${Sender}", "${Sender}" + " sent a image to you.");
            MiPush.setImVoicePushParm("${Sender}", "${Sender}" + " sent you a voice message.");
            MiPush.setImVideoPushParm("${Sender}", "${Sender}" + " sent you a video message.");
            MiPush.setImFilePushParm("${Sender}", "${Sender}" + " sent a file to you.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    @Override
    public void onTerminate() {
        LoginDelegate.destroy();
    }
}
