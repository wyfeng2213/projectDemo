package com.cmcc.patient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmcc.patient.R;
import com.cmcc.patient.ui.base.BaseActivity;
import com.simple.commonadapter.ListViewAdapter;
import com.simple.commonadapter.viewholders.GodViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 健康管理
 * Date：2017/03/16 10:29
 * Author: wangyong
 */


public class HealthRecordActivity extends BaseActivity {
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.listview)
    ListView listview;
    List<String> mDatas = new ArrayList<>();
    ListViewAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_health_record);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    public void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvCenter.setText(getString(R.string.str_health_record));
        tvRight.setText(getString(R.string.str_add));
        tvRight.setVisibility(View.VISIBLE);
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, HealthRecordActivity.class);
        context.startActivity(intent);
    }

    public void initData() {
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
//                ServiceDetailActivity.enterActivity(mcontext);

            }
        });

    }

    @OnClick({R.id.tv_left, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                AddHealthRecordActivity.enterActivity(this);
                break;
        }
    }
}