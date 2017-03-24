package com.cmcc.mobilehealthcare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmcc.mobilehealthcare.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shuyang on 2017/3/16.
 */

public class VoiceCallActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.paitent_info)
    TextView paitentInfo;
    @BindView(R.id.diagnose_table)
    TextView diagnoseTable;
    @BindView(R.id.dial)
    TextView dial;
    @BindView(R.id.silence_switch)
    TextView silenceSwitch;
    @BindView(R.id.cancle)
    TextView cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicecall);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_back,R.id.paitent_info, R.id.diagnose_table, R.id.dial, R.id.silence_switch, R.id.cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.paitent_info:
                break;
            case R.id.diagnose_table:
                break;
            case R.id.dial:
                break;
            case R.id.silence_switch:
                break;
            case R.id.cancle:
                break;
        }
    }
}
