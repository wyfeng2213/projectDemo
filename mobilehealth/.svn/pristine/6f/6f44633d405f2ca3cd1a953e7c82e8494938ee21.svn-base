package com.cmcc.patient.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.cmcc.patient.R;
import com.cmcc.patient.ui.activity.ServiceDetailActivity;
import com.simple.commonadapter.ListViewAdapter;
import com.simple.commonadapter.viewholders.GodViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ServiceFragment extends Fragment {
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.custom_view)
    XRefreshView refreshView;

    private View view;// 缓存页面
    ListViewAdapter mAdapter;
    List<String> mDatas = new ArrayList<>();
    public static long lastRefreshTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_service, container, false);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);// 先移除
            }
        }
        ButterKnife.bind(this, view);
        initView();
        initData();

        return view;
    }

    private void initView() {
        // 设置是否可以下拉刷新
        refreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        refreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        refreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        refreshView.setAutoRefresh(true);

        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.stopRefresh();
                        lastRefreshTime = refreshView.getLastRefreshTime();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        refreshView.stopLoadMore();
                    }
                }, 2000);
            }

        });
    }

    private void initData() {
        tvCenter.setText(getString(R.string.service));
        for (int i = 0; i < 10; i++) {
            mDatas.add(" ");
        }
        mAdapter = new ListViewAdapter<String>(R.layout.item_service) {
            @Override
            protected void onBindData(GodViewHolder viewHolder, int position, String item) {
//                viewHolder
//                        .setText(R.id.textview, item)             // 设置文本内容
//                        .setImageResource(R.id.imageview, R.drawable.big_smile); // 设置图片资源
            }
        };
        mAdapter.addItems(mDatas);
        listview.setAdapter(mAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceDetailActivity.enterActivity(getActivity());

            }
        });
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.i("slide", "HomeFragment--onPause");
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.i("slide", "HomeFragment--onStop");
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("slide", "HomeFragment--onDestroy");
    }
}
