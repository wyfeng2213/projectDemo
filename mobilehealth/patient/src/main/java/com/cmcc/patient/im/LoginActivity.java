package com.cmcc.patient.im;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmcc.patient.R;
import com.cmcc.patient.ui.activity.MainActivity;
import com.justalk.cloud.juslogin.LoginDelegate;
import com.justalk.cloud.juspush.Gcm;
import com.justalk.cloud.juspush.MiPush;
import com.justalk.cloud.lemon.MtcApi;
import com.justalk.cloud.lemon.MtcDiag;
import com.justalk.cloud.lemon.MtcVer;
import com.justalk.cloud.zmf.Zmf;

import org.json.JSONObject;
import org.json.JSONTokener;


public class LoginActivity extends Activity implements LoginDelegate.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermission();

        mEditTextUserName = (EditText) findViewById(R.id.et_username);
        mEditTextServerAddr = (EditText) findViewById(R.id.et_server);
        mEditTextPassword = (EditText) findViewById(R.id.et_password);
        mButtonChooseServer = (Button) findViewById(R.id.btn_chooseServer);
        mButtonLogin = (Button) findViewById(R.id.btn_login);
        mButtonChat = (Button) findViewById(R.id.btn_chat);
        mEditTextBandwidth = (EditText) findViewById(R.id.et_bandwidth);
        mButtonDetect = (Button) findViewById(R.id.btn_detect);

        LoginDelegate.setCallback(this);

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        if (mDiagTptStatisticsReceiver == null) {
            mDiagTptStatisticsReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int sendBw = 0;
                    int recvBw = 0;
                    try {
                        String info = intent.getStringExtra(MtcApi.EXTRA_INFO);
                        JSONObject json = (JSONObject) new JSONTokener(info).nextValue();
                        sendBw = json.getInt(MtcDiag.MtcDiagSendBandwidthKey);
                        recvBw = json.getInt(MtcDiag.MtcDiagReceiveBandwidthKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    mEditTextBandwidth.setText(String.format("out:%d \t in:%d", sendBw, recvBw));
                }
            };
            broadcastManager.registerReceiver(mDiagTptStatisticsReceiver, new IntentFilter(MtcDiag.MtcDiagTptTestStatisticsNotification));
        }

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT > 22 && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LoginDelegate.init(this, getString(R.string.JusTalkCloud_AppKey));
                    MiPush.setImTextPushParm("${Sender}", "${Text}");
                    MiPush.setImImagePushParm("${Sender}", "${Sender}" + " sent a image to you.");
                    MiPush.setImVoicePushParm("${Sender}", "${Sender}" + " sent you a voice message.");
                    MiPush.setImVideoPushParm("${Sender}", "${Sender}" + " sent you a video message.");
                    MiPush.setImFilePushParm("${Sender}", "${Sender}" + " sent a file to you.");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LoginDelegate.enterBackground();
    }

    protected void onResume() {
        super.onResume();
        LoginDelegate.enterForeground();
    }

    public void onLogin(View view) {
        if (LoginDelegate.getInitState() == LoginDelegate.InitStat.MTC_INIT_FAIL) {
            checkPermission();
            return;
        }

        if (mLogined) {
            logout();
        } else {
            login();
        }
    }

    public void onChooseServer(View view) {
        mEditTextServerAddr.setText("http:router.justalkcloud.com:8080");
    }

    public void onChat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void onDetect(View view) {
        if (mTestStarted) {
            MtcDiag.Mtc_DiagTptTestStop();
            mEditTextBandwidth.setText("");
        } else {
            MtcDiag.Mtc_DiagTptTestStart();
        }
        mTestStarted = !mTestStarted;
    }

    public void showSDKVersion(View v) {
        if (LoginDelegate.getInitState() == LoginDelegate.InitStat.MTC_INIT_FAIL) {
            checkPermission();
            return;
        }

        String avatarVer = MtcVer.Mtc_GetAvatarVersion();
        String melonVer = MtcVer.Mtc_GetMelonVersion();
        String mtcVer = MtcVer.Mtc_GetVersion();
        String zmfVer = Zmf.getVersion();
        String jsmVer = MtcVer.Mtc_GetJsmVersion();

        String msg = String.format("Avatar: %s \nMelon: %s \nLemon: %s \nZmf: %s \nJsm: %s", avatarVer, melonVer, mtcVer, zmfVer, jsmVer);
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setMessage(msg);
        builder.setNegativeButton("OK", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean mLogined = false;
    private boolean mIsActive = false;

    private EditText mEditTextUserName;
    private EditText mEditTextServerAddr;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonChooseServer;
    private Button mButtonChat;
    private ProgressDialog mProgressDialog;

    private boolean mTestStarted = false;
    private EditText mEditTextBandwidth;
    private Button mButtonDetect;
    private static BroadcastReceiver mDiagTptStatisticsReceiver;

    private void login() {
        String user = mEditTextUserName.getText().toString();
        String password = mEditTextPassword.getText().toString();
        String network = mEditTextServerAddr.getText().toString();

        if (user.length() == 0 || password.length() == 0 || network.length() == 0) {
            Toast.makeText(this, "You must enter username password and server", Toast.LENGTH_SHORT).show();
            return;
        }

        if (LoginDelegate.login(user, password, network)) {
            mProgressDialog = ProgressDialog.show(this, null,
                    getString(R.string.logging_in));
        }
    }

    private void logout() {
        if (LoginDelegate.logout()) {
            mProgressDialog = ProgressDialog.show(this, null,
                    getString(R.string.logging_out));
        }
    }

    @Override
    public void mtcLoginOk() {
        loginOk();
    }

    @Override
    public void mtcLoginDidFail() {
        loginFailed();
    }

    @Override
    public void mtcLogoutOk() {
        logoutOk();
    }

    @Override
    public void mtcLogouted() {
        logouted();
    }

    @Override
    public void mtcAuthRequire(String id, String nonce) {
        String key =
                "MIIEpAIBAAKCAQEAtzJF3SdDDZNuMit61hrIrDNROPhCnma9lp1gPaBF0tZGvn2X" + "\n" +
                        "GQWtlrQ1XEGbfTrpN1whsVB/e8SBWdmNHwE6Ze67fzYAw8B5Ul+bkO4lx79LL2If" + "\n" +
                        "n2oQ6doW9NYCCFt0CHv4A9esU1zB8SNKZazKfU/u3n/UEEInd/cJ/vMiuAWGSSPa" + "\n" +
                        "wLCqJJT7Ly+/Cgq9vK4jdX0YohBA7/ZSr7jx+9Zs2Lj4/L+y6lKR6UdXoTY0nJKf" + "\n" +
                        "jSEZCxwFCPh57snvg90fDyizn58EI1dZ977+bG5oD1zE2O4CmhLaX4tQiQCioZeP" + "\n" +
                        "D+iHTsXWYP9u8l2J/PBxVObqLBPAcqV4UyslZwIDAQABAoIBADd9X9IUEWhsTsWd" + "\n" +
                        "i/CMXlpilOinsi4eurCDbOJdyKiLRRRwIDNxF9p9LWiLatis3nVpT79Qvby0keWw" + "\n" +
                        "UuGgUpsLi/mFVwf0JguAcDOfHwx48gIhO6jizMq4x5lTtXvoj6X+PuqTClyZzRkI" + "\n" +
                        "coGHrDH240i7+XUPRLs+teVmqg6JAlVh2t3WjI7967I1wgzywchFWMSTftilULjl" + "\n" +
                        "7NKZEn0anDIJoN4Rgy0KSX9pzHHaEMmkD9bdpx/XlaXjaWpEfLB9frl8XEweixo8" + "\n" +
                        "R55Hpowk/Q4Qk73+xTvUmSO1XktyEWAumGgF1jWla5Z/5D3CadSgfJcfeePMXxCy" + "\n" +
                        "YC7TSxECgYEA6G/nOnrzNMe0dem8nt++86LOPn1wKqr0FV+AelNM2gzsoMtTUazX" + "\n" +
                        "C5Jyt6Xdq8+Y/xQ8WXmRziXlAS5753RenHbR9pY5XXP0AeMvMW4tlngGtKk0c/Rt" + "\n" +
                        "2W+eF7EI+sgI9g9TMRPjbvUGQDZl8gr2a94Q6u15tgYFJpjx8A3d1akCgYEAycR/" + "\n" +
                        "XCwVnGRqQTRA3cRhfz0usF3pyESqOoVfzzK0bSyOJAv7FOTd0V1MNhzwoWPwdTrl" + "\n" +
                        "5CGzTGxyaHZpi+sRVg5X/XolYwjgXrFKCckxmnVYlYFY30iegJfu3GR6phyTlwgc" + "\n" +
                        "nSQ2vfSseLnHTFDlrBEy/56H2MoQ4qXad5Sh7I8CgYEAsbDijyVxCbdl8QJ37OjV" + "\n" +
                        "vMGIc+NHPYclQ7WXrWxDAysANshZcMX2O+WAB38ooHD64H3iyPAUFAmKMUYM+NtQ" + "\n" +
                        "fMKlLqKXRicfsdWwvVQiS7aEQdZcwAxrcd9Pd4Mifz0vBJSgn5M5uhhc5/fuJYRV" + "\n" +
                        "8A561m4nLo0ZoPEpe7/OB8kCgYBvsplzNHCOUMTF7iCO5N24q+1B8+utU94NYbLF" + "\n" +
                        "qONboRPbfsp0KbNm6Uh8mI7aOdJvg7irD8EL6Ol5TTxnGi5RvsUVbV5vMgXMRkef" + "\n" +
                        "nUMZqCbvNVk22yPsOrAgUHvZo+5M6U+16stnY6FrgCWF6S8Mj8T04BWCfXLVlk2Y" + "\n" +
                        "b68onwKBgQDN2yjNq+B3FBI91DQGoHPWqsSbMPed/TJ6pGiSftKeckyA35tQVMAO" + "\n" +
                        "JoBCLU0G5Z2KWuqIFphjcg+YjR/Agsu/0v7ZRVyU1bDjmHW7MtBOLXPTB2QB8DF7" + "\n" +
                        "SeCrYJLUItyJaqQmxAaGQtDp8+dqm0BywAwoB2AgqDjXhph1xWMQIA==" + "\n";

        String code = Signer.signWithKey(key, id, nonce, 3600);
        LoginDelegate.promptAuthCode(code);
    }

    private void loginOk() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
        if (mEditTextUserName.getText().toString().equals("")) {
            mEditTextUserName.setText(sp.getString("username", ""));
            mEditTextPassword.setText(sp.getString("password", ""));
            mEditTextServerAddr.setText(sp.getString("server", ""));
        }
        updateLoginState(true);
        Editor editor = sp.edit();
        editor.putString("username", mEditTextUserName.getText().toString());
        editor.putString("password", mEditTextPassword.getText().toString());
        editor.putString("server", mEditTextServerAddr.getText().toString());
        editor.commit();

        MiPush.start(getApplicationContext(), getString(R.string.MiPush_AppId), getString(R.string.MiPush_AppKey));
        Gcm.start(getApplicationContext(), getString(R.string.gcm_sender_id));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void loginFailed() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();
    }

    private void logoutOk() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        Toast.makeText(this, R.string.logout_ok, Toast.LENGTH_LONG).show();
        updateLoginState(false);
        MiPush.stop(getApplicationContext());
        Gcm.stop(getApplicationContext());
    }

    private void logouted() {
        Toast.makeText(this, R.string.logouted, Toast.LENGTH_LONG).show();
        updateLoginState(false);
        MiPush.stop(getApplicationContext());
        Gcm.stop(getApplicationContext());
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    private void updateLoginState(boolean login) {
        mLogined = login;
        if (login) {
            mButtonLogin.setText(R.string.logout);
            mButtonChat.setVisibility(View.VISIBLE);
            mEditTextBandwidth.setVisibility(View.VISIBLE);
            mButtonDetect.setVisibility(View.VISIBLE);
            mEditTextUserName.setEnabled(false);
            mEditTextPassword.setEnabled(false);
            mEditTextServerAddr.setEnabled(false);
            mButtonChooseServer.setEnabled(false);
        } else {
            mButtonLogin.setText(R.string.login);
            mButtonChat.setVisibility(View.GONE);
            mEditTextBandwidth.setVisibility(View.GONE);
            mButtonDetect.setVisibility(View.GONE);
            mEditTextUserName.setEnabled(true);
            mEditTextPassword.setEnabled(true);
            mEditTextServerAddr.setEnabled(true);
            mButtonChooseServer.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (LoginDelegate.getInitState() == LoginDelegate.InitStat.MTC_INIT_SUCCESS)
            LoginDelegate.logout();
    }
}
