package com.cmcc.mobilehealthcare.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmcc.requestlibrary.utils.MessageBuilder;


/**
 * @author shuyang on 15/7/11.
 */
public abstract class BaseFragment extends NoBugFragment implements Handler.Callback {


    public Dialog diaolog;
    // 消息句柄
    public Handler handler;
//    /**
//     * 防止内存泄漏的内部内
//     */
//    protected class MyHandler extends Handler {
//        public final WeakReference<Activity> mActivity;
//
//        public MyHandler(Activity activity) {
//            mActivity = new WeakReference<Activity>(activity);
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(this);
    }

    @Override
    public View oncreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    /**
     * 消息处理
     */
    @Override
    public boolean handleMessage(Message msg) {
        Bundle data = msg.getData();
//        Activity activity = handler.mActivity.get();
//        if (activity == null) {
//            return false;
//        }
        switch (msg.what) {
            case MessageBuilder.SHOW_PROCESS_DIALOG_MSG:
                System.out.println("content-------------:" + data.getString("content"));
                // 显示进度对话框
                boolean canclable = true;
                if (data == null) {
                    data = new Bundle();
                    data.putString("content", "数据处理中......");
                } else {
                    canclable = data.getBoolean("canclable");
                }
                //同时调用进度栏时，取消前一个进
                if (diaolog != null && diaolog.isShowing()) {
                    diaolog.dismiss();
                }
                try {
                    diaolog = ProgressDialog.show(getActivity(), "", data.getString("content"), true, canclable,
                            new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {

                                }
                            });
                    diaolog.setCanceledOnTouchOutside(false);
                    diaolog.show();
                } catch (Exception e) {
                    try {
                        diaolog = ProgressDialog.show(getActivity(), "", data.getString("content"), true, canclable,
                                new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {

                                    }
                                });
                        diaolog.setCanceledOnTouchOutside(false);
                        diaolog.show();
                    } catch (Exception showe) {

                    }
                }
                break;
            case MessageBuilder.DISMISS_PROCESS_DIALOG_MSG:
                // 取消进度对话框
                if (diaolog != null && diaolog.isShowing()) {
                    diaolog.dismiss();
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 初始化事件
     */
    public abstract void initListener();

    /**
     * 初始化数据
     */
    public abstract void initData();
}
