package com.cmcc.mobilehealthcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.mobilehealthcare.R;
import com.cmcc.mobilehealthcare.fragment.FiveFragment;
import com.cmcc.mobilehealthcare.fragment.FourFragment;
import com.cmcc.mobilehealthcare.fragment.InquiryFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity{
    @BindView(R.id.frameLayout) FrameLayout frameLayout;
    @BindView(R.id.aurora) TextView aurora;
    @BindView(R.id.serve) TextView serve;
    @BindView(R.id.me) TextView me;
//    private IndexFragment oneFragment=new IndexFragment();
    private InquiryFragment inquiryFragment=new InquiryFragment();
    private FourFragment fourFragment=new FourFragment();
    private FiveFragment fiveFragment=new FiveFragment();
    private List<Fragment> fragments=new ArrayList<>();
    private List<TextView> views=new ArrayList<>();
    private int current;
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        views.get(current).setSelected(true);
        for(int i=0;i<fragments.size();i++){
            TextView textView=views.get(i);
            textView.setId(i);
            textView.setOnClickListener((v)->{
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment=fragments.get(v.getId());
                ft.replace(R.id.frameLayout, fragment);
                ft.commit();
                views.get(current).setSelected(false);
                views.get(v.getId()).setSelected(true);
                current = v.getId();
            });
        }
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
//        fragments.add(oneFragment);
        fragments.add(inquiryFragment);
        fragments.add(fourFragment);
        fragments.add(fiveFragment);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,fragments.get(0));
        ft.commit();
        views.add(aurora);
        views.add(serve);
        views.add(me);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 连续按两次返回键退出应用
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出移动医疗", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
//            finish();
//            System.exit(0);
            Intent intent = new Intent(this,QuitActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
