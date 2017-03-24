package com.cmcc.patient.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cmcc.healthlibrary.utils.ImageUtils;
import com.cmcc.patient.R;
import com.cmcc.patient.ui.activity.HealthRecordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MineFragment extends Fragment {
    @BindView(R.id.layout_health_record)
    RelativeLayout layoutHealthRecord;
    @BindView(R.id.img_head)
    ImageView imgHead;
    private View view;// 缓存页面

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("slide", "HomeFragment--onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("slide", "HomeFragment-onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, container, false);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);// 先移除
            }
        }
        ButterKnife.bind(this, view);

        com.cmcc.healthlibrary.utils.ImageUtils.Loader(getActivity(), R.mipmap.default_icon, imgHead, ImageUtils.LOAD_ROUND);
        return view;
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

    @OnClick({R.id.tv_rightarrow, R.id.layout_health_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_rightarrow:
                break;
            case R.id.layout_health_record:
                HealthRecordActivity.enterActivity(getActivity());
                break;
        }
    }
}
