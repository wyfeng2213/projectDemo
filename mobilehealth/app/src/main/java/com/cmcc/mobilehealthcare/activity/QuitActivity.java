package com.cmcc.mobilehealthcare.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * 使用singleTask退出应用
 * @author shuyang on 2016/3/16.
 */
public class QuitActivity extends Activity {
    private static final String TAG_EXIT ="exit";
    private static final String EXITACTION = "action.exit";
    private ExitReceiver exitReceiver = new ExitReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXITACTION);
        registerReceiver(exitReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent2 = new Intent();
        intent2.setAction(QuitActivity.EXITACTION);
        //发送广播
        sendBroadcast(intent2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(exitReceiver);
    }
    //广播接收者
    class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }

    }
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if(intent!=null) {
//            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
//            if (isExit) {
//                this.finish();
//            }
//        }
//    }
}

