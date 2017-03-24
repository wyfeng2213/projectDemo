package com.cmcc.patient.modle;


import com.cmcc.requestlibrary.bean.MaintainUserInfoResult;

import rx.Subscriber;

/**
 * Created by cmmx on 2016/10/20.
 */
public interface LoginModel {
    void getData(Subscriber<MaintainUserInfoResult> subscriber, String cardNo, String password, String equipmentData);
//void getData(Subscriber<LoginInfoResult> subscriber, String userName, String password);
}
