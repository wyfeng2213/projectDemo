package com.cmcc.patient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmcc.healthlibrary.utils.DateUtils;
import com.cmcc.patient.R;
import com.cmcc.patient.ui.base.BasePhotoActivity;
import com.cmcc.patient.ui.view.CalendarDialogListener;
import com.cmcc.patient.ui.view.DialogGLC;
import com.cmcc.patient.utils.CustomHelper;
import com.cmcc.patient.utils.DialogUtils;
import com.jph.takephoto.model.TResult;
import com.orhanobut.logger.Logger;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Date：2017/03/16 11:16
 * Author: wangyong
 */


public class AddHealthRecordActivity extends BasePhotoActivity {
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_select_calendar)
    TextView tvSelectCalendar;
    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.layout_showpop)
    LinearLayout layoutShowpop;
    private CustomHelper customHelper;
    private DialogGLC mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_healthrecord);
        View contentView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
        customHelper = CustomHelper.of(contentView);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String path = result.getImage().getCompressPath();
        Logger.e(path);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(getString(R.string.str_back));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.str_finish));
        tvCenter.setText(getString(R.string.str_health_record));
        tvDate.setText(DateUtils.getStringDateShort());
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, AddHealthRecordActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_left, R.id.tv_right, R.id.layout_showpop, R.id.tv_select_calendar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                finish();
                break;
            case R.id.layout_showpop:
                showPop();
                break;
            case R.id.tv_select_calendar:
                showInDialog();
                break;
        }
    }

    public void showPop() {
        DialogUtils.getInstance().showBottomPop(this, R.layout.pop_photo);
        View view = DialogUtils.getInstance().getPopView();
        TextView tvPickBySelect = (TextView) view.findViewById(R.id.btnPickBySelect);
        tvPickBySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customHelper.onClick(view, getTakePhoto());
                DialogUtils.getInstance().dismissPopupWindow();
            }
        });
        TextView tvPickByTake = (TextView) view.findViewById(R.id.btnPickByTake);
        tvPickByTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customHelper.onClick(view, getTakePhoto());
                DialogUtils.getInstance().dismissPopupWindow();
            }
        });
    }

    private void showInDialog() {
        mDialog = new DialogGLC(this);
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        } else {
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.show();
            //better initialize NumberPickerView's data (or set a certain value)
            // every time setting up reusable dialog
            mDialog.initCalendar();
        }
        mDialog.setDialogListener(dialogListener);
    }


    CalendarDialogListener dialogListener = new CalendarDialogListener() {
        @Override
        public void getdata(Calendar calendar) {
            String data = calendar.get(Calendar.YEAR) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH);
            tvDate.setText(data);
        }
    };


}