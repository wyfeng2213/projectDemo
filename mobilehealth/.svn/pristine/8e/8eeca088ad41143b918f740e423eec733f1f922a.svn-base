package com.cmcc.mobilehealthcare.model;

import com.cmcc.mobilehealthcare.httprequest.NetRequest;
import com.cmcc.requestlibrary.bean.MaintainUserInfoResult;

import rx.Subscriber;

/**
 * @author shuyang on 2016/10/20.
 */
public class LoginModelImpl implements LoginModel{
    @Override
    public void getData(Subscriber<MaintainUserInfoResult> subscriber, String cardNo, String password, String equipmentData) {
        NetRequest.getInstance().login(subscriber,cardNo,password,equipmentData);
    }
}
