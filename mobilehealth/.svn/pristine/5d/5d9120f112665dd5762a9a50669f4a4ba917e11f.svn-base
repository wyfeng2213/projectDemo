package com.cmcc.requestlibrary.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * The type Progress dialog handler.
 *
 * @author shuyang on 16/3/10.
 */
public class ProgressDialogHandler extends Handler {

    /**
     * The constant SHOW_PROGRESS_DIALOG.
     */
    public static final int SHOW_PROGRESS_DIALOG = 1;
    /**
     * The constant DISMISS_PROGRESS_DIALOG.
     */
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    /**
     * Instantiates a new Progress dialog handler.
     *
     * @param context                 the context
     * @param mProgressCancelListener the m progress cancel listener
     * @param cancelable              the cancelable
     */
    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog(){
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setMessage("数据请求中...");
            pd.setCancelable(cancelable);
            pd.setCanceledOnTouchOutside(false);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    private void dismissProgressDialog(){
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
