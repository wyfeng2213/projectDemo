package com.cmcc.mobilehealthcare.presenter;

import android.content.Context;

import com.cmcc.mobilehealthcare.model.LoginModel;
import com.cmcc.mobilehealthcare.model.LoginModelImpl;
import com.cmcc.mobilehealthcare.view.LoginView;
import com.cmcc.requestlibrary.bean.MaintainUserInfoResult;
import com.cmcc.requestlibrary.subscribers.ProgressSubscriber;
import com.cmcc.requestlibrary.subscribers.SubscriberOnNextListener;


/**
 * @author shuyang on 2016/10/20.
 */
public class LoginPresenterImpl implements LoginPresenter{
    private Context context;
    private LoginModel loginModel;
    private LoginView loginView;
    public LoginPresenterImpl(Context context, LoginView loginView){
        this.context=context;
        this.loginView=loginView;
        loginModel=new LoginModelImpl();
    }
    @Override
    public void getData(String cardNo, String password, String equipmentData) {
        loginModel.getData(new ProgressSubscriber<MaintainUserInfoResult>(new SubscriberOnNextListener<MaintainUserInfoResult>() {
                    @Override
                    public void onNext(MaintainUserInfoResult muir) {
                        loginView.onSuccess(muir);
                    }

                    @Override
                    public void onFailure() {

                    }
                }, context,true),
                cardNo,password,equipmentData);
    }
}
