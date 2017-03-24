
package com.cmcc.requestlibrary.utils;

import android.os.Bundle;
import android.os.Message;

/**
 * android 消息对象构造器(Message)
 *
 * @author luoyizhou
 */
public class MessageBuilder {

    /**
     * The constant DISMISS_PROCESS_DIALOG_MSG.
     */
    public static final int DISMISS_PROCESS_DIALOG_MSG = 301;// 取消进度条窗口

    /**
     * The constant SHOW_PROCESS_DIALOG_MSG.
     */
    public static final int SHOW_PROCESS_DIALOG_MSG = 302; // 可取消进度条弹出窗口

    /**
     * The constant SHOW_PROCESS_NO_CANCEL_DIALOG_MSG.
     */
    public static final int SHOW_PROCESS_NO_CANCEL_DIALOG_MSG = 301;// 不可取消进度条弹出窗口

    /**
     * Create msg message.
     *
     * @param msgType the msg type
     * @param content the content
     * @return the message
     */
    public static Message createMsg(int msgType, String content) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        if (msgType == DISMISS_PROCESS_DIALOG_MSG) {
            msg.what = DISMISS_PROCESS_DIALOG_MSG;
        } else if (msgType == SHOW_PROCESS_DIALOG_MSG) {
            msg.what = SHOW_PROCESS_DIALOG_MSG;
            bundle.putString("content", content);
            bundle.putBoolean("canclable", true);
            msg.setData(bundle);
        } else if (msgType == SHOW_PROCESS_NO_CANCEL_DIALOG_MSG) {
            msg.what = SHOW_PROCESS_DIALOG_MSG;
            bundle.putString("content", content);
            bundle.putBoolean("canclable", false);
            msg.setData(bundle);
        }
        return msg;
    }

}
