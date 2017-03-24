package com.cmcc.mobilehealthcare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cmcc.mobilehealthcare.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmmx on 2017/3/15.
 */

public class InquiryFragment extends BaseFragment {
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    private List<TextView> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MessageFragment messageFragment = new MessageFragment();
    private PhoneFragment phoneFragment = new PhoneFragment();
    private int current;

    @Override
    public void initListener() {
        titles.get(current).setSelected(true);
        for (int i = 0; i < fragments.size(); i++) {
            TextView textView = titles.get(i);
            textView.setId(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    Fragment fragment = fragments.get(v.getId());
                    ft.replace(R.id.frameLayout, fragment);
                    ft.commit();
                    titles.get(current).setSelected(false);
                    titles.get(v.getId()).setSelected(true);
                    current = v.getId();
                }
            });
        }
    }

    @Override
    public void initData() {
        titles.add(message);
        titles.add(phone);
        titles.get(0).setSelected(true);
        fragments.add(messageFragment);
        fragments.add(phoneFragment);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragments.get(0));
        ft.commit();
    }

    @Override
    public View oncreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_inquiry, null);
        ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragments.get(current));
        ft.commit();
    }
}