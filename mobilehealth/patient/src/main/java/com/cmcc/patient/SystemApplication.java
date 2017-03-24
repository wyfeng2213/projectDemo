package com.cmcc.patient;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.justalk.cloud.juscall.JusCallConfig;
import com.justalk.cloud.juscall.MtcCallDelegate;
import com.justalk.cloud.jusdoodle.DoodleDelegate;
import com.justalk.cloud.juslogin.LoginDelegate;
import com.justalk.cloud.juspush.MiPush;

import java.util.Stack;

/**
 * Description:
 * Date：2017/03/15 10:30
 * Author: wangyong
 */

public class SystemApplication extends Application implements LoginDelegate.InitStat {
    private static Stack<Activity> mActivityStack;
    private static SystemApplication sysApplication;

    public static SystemApplication getInstance() {
        if (sysApplication == null) {
            sysApplication = new SystemApplication();
        }
        return sysApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化菊风通讯
        initIM();
        // 异常信息收集
//        CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
//        crashHandlerUtil.init(this);
//        crashHandlerUtil.setCrashTip(getString(R.string.str_crash_tip));
    }

    private void initIM() {
        if (Build.VERSION.SDK_INT > 22 && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) return;

        if (LoginDelegate.init(this, getString(R.string.JusTalkCloud_AppKey)) != MTC_INIT_SUCCESS)
            return;

        MiPush.setImTextPushParm("${Sender}", "${Text}");
        MiPush.setImImagePushParm("${Sender}", "${Sender}" + " sent a image to you.");
        MiPush.setImVoicePushParm("${Sender}", "${Sender}" + " sent you a voice message.");
        MiPush.setImVideoPushParm("${Sender}", "${Sender}" + " sent you a video message.");
        MiPush.setImFilePushParm("${Sender}", "${Sender}" + " sent a file to you.");

        if (LoginDelegate.init(this, getString(R.string.JusTalkCloud_AppKey)) != MTC_INIT_SUCCESS)
            return;
        MtcCallDelegate.init(this);
        JusCallConfig.setBackIntentAction("com.justalk.cloud.sample.call.action.backfromcall");
        MiPush.setCallPushParm();
        DoodleDelegate.init(this);
        String[] imgs = new String[]{"/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_0.jpg",
                "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_1.jpg",
                "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_2.jpg",
                "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_3.jpg",
                "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_4.jpg",
                "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_5.jpg",
                "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_6.jpg",
                "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_7.jpg",
                "/sdcard/Android/data/com.justalk.cloud.sample.android.call/files/mtc/bgimage/background_8.jpg"};
        DoodleDelegate.setBackgroundImages(imgs);
    }

    /**
     * get current activity
     */
    public Activity getCurActivity() {
        return mActivityStack.lastElement();
    }


    // 移除activity
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    // 将当前Activity推入栈中
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    // 退出栈中所有Activity
    public void clearAllActivity() {
        while (!mActivityStack.isEmpty()) {
            Activity activity = mActivityStack.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }


    @Override
    public void onTerminate() {
        LoginDelegate.destroy();
    }
}
