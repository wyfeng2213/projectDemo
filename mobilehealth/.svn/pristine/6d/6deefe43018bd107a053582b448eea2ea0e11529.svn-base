package com.cmcc.mobilehealthcare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmcc.mobilehealthcare.R;
import com.cmcc.mobilehealthcare.presenter.LoginPresenter;
import com.cmcc.mobilehealthcare.presenter.LoginPresenterImpl;
import com.cmcc.mobilehealthcare.view.LoginView;
import com.cmcc.requestlibrary.bean.MaintainUserInfoResult;
import com.cmcc.requestlibrary.http.HttpMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author shuyang on 2016/6/29.
 */
public class IndexFragment extends BaseFragment implements LoginView {
    @BindView(R.id.et_cardno)
    EditText et_cardno;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.bt_login)
    Button bt_login;

    private LoginPresenter presenter;

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
//        bt_login.setOnClickListener((v)->{
//            presenter.getData(et_cardno.getText().toString(),et_password.getText().toString(), HttpMethods.getInstance().getLocalInfo(get_Activity()));
//        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
//        et_cardno=(EditText)view.findViewById(R.id.et_cardno);
//        et_password=(EditText)view.findViewById(R.id.et_password);
//        bt_login=(Button)view.findViewById(R.id.bt_login);
        presenter = new LoginPresenterImpl(get_Activity(), this);
    }

    @Override
    public View oncreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(get_Activity(), R.layout.fragment_workorder, null);
//        TextView textView=new TextView(get_Activity());
//        textView.setText("one");
//        //初始化数据
//        initData();
//        //初始化事件
//        initListener();
        ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;
    }

    @Override
    public void onSuccess(MaintainUserInfoResult muir) {
        Toast.makeText(get_Activity(), muir.session, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_login)
    public void onClick() {
        presenter.getData(et_cardno.getText().toString(),et_password.getText().toString(), HttpMethods.getInstance().getLocalInfo(get_Activity()));
    }
}
