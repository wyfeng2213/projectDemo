package com.cmcc.patient.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.cmcc.healthlibrary.utils.ToastUtil;
import com.cmcc.healthlibrary.view.WebViewFragment;
import com.cmcc.patient.R;
import com.cmcc.patient.ui.adapter.MyViewPagerAdapter;
import com.cmcc.patient.ui.base.BaseFragmentActivity;
import com.cmcc.patient.ui.fragment.HomeFragment;
import com.cmcc.patient.ui.fragment.MineFragment;
import com.cmcc.patient.ui.fragment.ServiceFragment;

import java.util.ArrayList;

public class MainActivity extends BaseFragmentActivity implements OnPageChangeListener {
    private ViewPager pager;
    private PagerAdapter mAdapter;
    WebViewFragment webViewFragment;
    HomeFragment homeFragment;
    ServiceFragment serviceFragment;
    MineFragment mineFragment;

    private ArrayList<Fragment> fragments;
    private ArrayList<RadioButton> title = new ArrayList<RadioButton>();// 三个标题

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//
        initView();// 初始化控件
        initTitle();
        initViewPager();
    }

    /**
     * 初始化视图
     */
    public void initView() {
        pager = (ViewPager) findViewById(R.id.pager);// 初始化控件
        fragments = new ArrayList<Fragment>();// 初始化数据

        webViewFragment = new WebViewFragment();
        webViewFragment.addWebViewURL(webViewFragment, "http://www.baidu.com");
        serviceFragment = new ServiceFragment();
        mineFragment = new MineFragment();

//        fragments.add(homeFragment);
        fragments.add(webViewFragment);
        fragments.add(serviceFragment);
        fragments.add(mineFragment);
    }

    @Override
    public void initData() {

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(mAdapter);
        pager.setOnPageChangeListener(this);
        pager.setCurrentItem(0);// 设置成当前第一个
    }

    /**
     * 初始化几个用来显示title的RadioButton
     */
    private void initTitle() {
        title.add((RadioButton) findViewById(R.id.title1));// 三个title标签
        title.add((RadioButton) findViewById(R.id.title2));
        title.add((RadioButton) findViewById(R.id.title3));
        title.get(0).setOnClickListener(new MyOnClickListener(0));// 设置响应
        title.get(1).setOnClickListener(new MyOnClickListener(1));
        title.get(2).setOnClickListener(new MyOnClickListener(2));
    }


    /**
     * 重写OnClickListener的响应函数，主要目的就是实现点击title时，pager会跟着响应切换
     *
     * @author llb
     */
    private class MyOnClickListener implements OnClickListener {
        private int index;

        public MyOnClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            pager.setCurrentItem(index);// 把viewpager的视图切过去，实现捏造title跟pager的联动
            title.get(index).setChecked(true);// 设置被选中，否则布局里面的背景不会切换
        }

    }

    /**
     * 下面三个是OnPageChangeListener的接口函数
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        Log.i("slide", "onPageSelected+agr0=" + arg0);
        title.get(arg0).setChecked(true);// 保持页面跟按钮的联动
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            exitTime = System.currentTimeMillis();
            ToastUtil.show(this, getString(R.string.str_exit_procedure));
        } else {
            finish();
//            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
