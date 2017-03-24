package com.cmcc.patient.im;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.cmcc.patient.R;
import com.justalk.cloud.juscall.JusCallConfig;
import com.justalk.cloud.juscall.MtcCallDelegate;
import com.justalk.cloud.jusdoodle.*;
import com.justalk.cloud.lemon.MtcCallDb;
import com.justalk.cloud.lemon.MtcCli;
import com.justalk.cloud.lemon.MtcCliConstants;

import java.io.File;
import java.util.Locale;


public class DialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        if (MtcCli.Mtc_CliGetState() != MtcCliConstants.EN_MTC_CLI_STATE_LOGINED) {
            finish();
            return;
        }

        checkPermission();

        mEditTextNumber = (EditText) findViewById(R.id.et_phone_number);

        mRadioGroupBitrate = (RadioGroup) findViewById(R.id.dial_bitrate);
        int mode = JusCallConfig.getBitrateMode();
        switch (mode) {
            case JusCallConfig.BITRATE_MODE_NANO:
                mRadioGroupBitrate.check(R.id.bitrate_nano);
                break;
            case JusCallConfig.BITRATE_MODE_MIN:
                mRadioGroupBitrate.check(R.id.bitrate_min);
                break;
            case JusCallConfig.BITRATE_MODE_LOW:
                mRadioGroupBitrate.check(R.id.bitrate_low);
                break;
            case JusCallConfig.BITRATE_MODE_NORMAL:
                mRadioGroupBitrate.check(R.id.bitrate_mid);
                break;
            case JusCallConfig.BITRATE_MODE_720P:
                mRadioGroupBitrate.check(R.id.bitrate_high);
                break;
            default:
                break;
        }

        mRadioGroupBitrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                // TODO Auto-generated method stub
                int value = 0;
                switch (id) {
                    case R.id.bitrate_nano:
                        value = JusCallConfig.BITRATE_MODE_NANO;
                        break;
                    case R.id.bitrate_min:
                        value = JusCallConfig.BITRATE_MODE_MIN;
                        break;
                    case R.id.bitrate_low:
                        value = JusCallConfig.BITRATE_MODE_LOW;
                        break;
                    case R.id.bitrate_mid:
                        value = JusCallConfig.BITRATE_MODE_NORMAL;
                        break;
                    case R.id.bitrate_high:
                        value = JusCallConfig.BITRATE_MODE_720P;
                        break;
                }
                JusCallConfig.setBitrateMode(value);
            }
        });

        mRadioGroupNack = (RadioGroup) findViewById(R.id.dial_nack);
        mRadioGroupNack.check(R.id.nack_low);
        mRadioGroupNack.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                // TODO Auto-generated method stub
                int value = 300;
                switch (id) {
                    case R.id.nack_low:
                        value = 300;
                        break;
                    case R.id.nack_mid:
                        value = 500;
                        break;
                    case R.id.nack_high:
                        value = 800;
                        break;
                }
                MtcCallDb.Mtc_CallDbSetVideoNackRttRange(100, value);
            }
        });

        mRadioGroupAutoAnswer = (RadioGroup) findViewById(R.id.dial_auto_answer);
        boolean autoAnswer = JusCallConfig.getIsAutoAnswerEnable();
        boolean autoAnswerWithVideo = JusCallConfig.getIsAutoAnswerWithVideo();
        if (autoAnswer) {
            if (autoAnswerWithVideo)
                mRadioGroupAutoAnswer.check(R.id.auto_answer_video);
            else
                mRadioGroupAutoAnswer.check(R.id.auto_answer_audio);
        } else
            mRadioGroupAutoAnswer.check(R.id.auto_answer_off);

        mRadioGroupAutoAnswer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                // TODO Auto-generated method stub
                boolean enable = false;
                boolean video = false;
                switch (id) {
                    case R.id.auto_answer_off:
                        enable = false;
                        video = false;
                        break;
                    case R.id.auto_answer_audio:
                        enable = true;
                        video = false;
                        break;
                    case R.id.auto_answer_video:
                        enable = true;
                        video = true;
                        break;
                }
                JusCallConfig.setAutoAnswer(enable, video);
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT < 23) return;

        boolean noRecord = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED;
        boolean noCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
        if (noRecord && noCamera) {
            String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, permissions, 0);
        } else if (noRecord) {
            String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
            ActivityCompat.requestPermissions(this, permissions, 0);
        } else if (noCamera) {
            String[] permissions = new String[]{Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, permissions, 0);
        }
    }

    public void onDialAudio(View view) {

        String number = mEditTextNumber.getText().toString();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Please enter number", Toast.LENGTH_SHORT).show();
            return;
        }
        MtcCallDelegate.call(number, null, null, false, null);
    }

    public void onDialVideo(View view) {

        String number = mEditTextNumber.getText().toString();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Please enter number", Toast.LENGTH_SHORT).show();
            return;
        }
        MtcCallDelegate.call(number, null, null, true, null);
    }

    public void onStartDoodle(View v) {
        DoodleDelegate.startDoodle(10);
    }

    public void onRecord(View v) {
        Button btn = (Button) v;
        if (bRecord) {
            DoodleDelegate.stopRecordDoodleVideo();
            btn.setText("Record Doodle");
        } else {
            DoodleDelegate.startRecordDoodleVideo(getFilePath());
            btn.setText("Stop Record");
        }

        bRecord = !bRecord;
    }

    private String getFilePath() {
        File fileDir = getExternalFilesDir(null);
        String dir = null;
        if (fileDir != null) {
            dir = fileDir.getAbsolutePath();
        } else {
            dir = getFilesDir().getAbsolutePath();
        }
        dir += "/mtc/doodleVideo/";
        fileDir = new File(dir);
        fileDir.mkdirs();

        Time time = new Time();
        time.set(System.currentTimeMillis());
        String fileName = String.format(Locale.getDefault(),
                "%04d-%02d-%02d-%02d-%02d-%02d", time.year, time.month,
                time.monthDay, time.hour, time.minute, time.second);

        return dir + fileName + ".mp4";
    }

    private EditText mEditTextNumber;
    private RadioGroup mRadioGroupBitrate;
    private RadioGroup mRadioGroupNack;
    private RadioGroup mRadioGroupAutoAnswer;

    private boolean bRecord = false;
}
