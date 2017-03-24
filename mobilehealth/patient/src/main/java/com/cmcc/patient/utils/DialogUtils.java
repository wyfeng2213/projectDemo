package com.cmcc.patient.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.cmcc.patient.R;

/**
 * Description:
 * Date：2017/03/24 15:54
 * Author: wangyong
 */

public class DialogUtils {
    public static DialogUtils dialogUtil;
    private PopupWindow mPopupWindow = null;
    private AlertDialog dialog;
    private View view;

    public static DialogUtils getInstance() {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtils();
        }
        return dialogUtil;
    }

    public View getPopView() {
        return view;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void showBottomPop(Activity context, int layoutId) {
        // 一个自定义的布局，作为显示的内容
        view = LayoutInflater.from(context).inflate(
                layoutId, null);
//        LinearLayout layout_pic = (LinearLayout) view.findViewById(R.id.layout_pop);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_bottom_in));

        mPopupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
// mPopupWindow.setAnimationStyle(R.style.popupwindow_anim);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        View parentView = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        mPopupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }


    public void showCenterPop(Activity context, int layoutId) {
        // 一个自定义的布局，作为显示的内容
        view = LayoutInflater.from(context).inflate(
                layoutId, null);
        mPopupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setAnimationStyle(R.style.popupwindow_anim);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        View parentView = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        mPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }

    public void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    private void showDailog(Activity context, int layoutId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        view = View.inflate(context, layoutId, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题
        dialog.show();
    }
}
