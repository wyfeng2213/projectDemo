package com.cmcc.patient.modle;

import com.cmcc.patient.httprequest.NetRequest;
import com.cmcc.requestlibrary.bean.MaintainUserInfoResult;

import rx.Subscriber;

/**
 * Created by cmmx on 2016/10/20.
 */
public class LoginModelImpl implements LoginModel {
    @Override
    public void getData(Subscriber<MaintainUserInfoResult> subscriber, String cardNo, String password, String equipmentData) {
//        HttpMethods.getInstance().getResult(subscriber,cardNo,password,equipmentData);
        NetRequest.getInstance().login(subscriber, cardNo, password, equipmentData);
    }
//@Override
//public void getData(Subscriber<LoginInfoResult> subscriber, String userName, String password) {
//    HttpMethods.getInstance().getResult1(subscriber,userName,password);
//}
}
