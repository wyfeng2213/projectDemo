package com.cmcc.patient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmcc.patient.im.ChatActivity;
import com.cmcc.patient.im.DialActivity;
import com.cmcc.patient.R;
import com.cmcc.patient.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Date：2017/03/15 17:46
 * Author: wangyong
 */

public class ServiceDetailActivity extends BaseActivity {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.len_age)
    TextView lenAge;
    @BindView(R.id.layout_pic)
    LinearLayout layoutPic;
    @BindView(R.id.layout_tell)
    LinearLayout layoutTell;
    @BindView(R.id.layout_video)
    LinearLayout layoutVideo;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        ButterKnife.bind(this);
        tvCenter.setText(R.string.service_detail);

        initView();
    }

    public void initView() {
        tvLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, ServiceDetailActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_left, R.id.layout_pic, R.id.layout_tell, R.id.layout_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.layout_pic:
                intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_tell:
                intent = new Intent(this, DialActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_video:
                intent = new Intent(this, DialActivity.class);
                startActivity(intent);
                break;
        }
    }
}
