package com.cmcc.patient.httprequest;

import com.cmcc.requestlibrary.bean.MaintainUserInfoResult;
import com.cmcc.requestlibrary.http.HttpMethods;

import rx.Observable;
import rx.Subscriber;

/**
 * @author shuyang on 2017/3/14.
 */

public class NetRequest {
    private static HttpMethods httpMethods;
    //在访问NetRequest时创建单例
    private static class SingletonHolder {
        private static final NetRequest INSTANCE = new NetRequest();
    }
    //获取单例
    public static NetRequest getInstance(){
        httpMethods=HttpMethods.getInstance();
        return NetRequest.SingletonHolder.INSTANCE;
    }
    /**
     * 登录
     */
    public void login(Subscriber<MaintainUserInfoResult> subscriber, String cardNo,
                      String password, String equipmentData){
        Observable observable=httpMethods.getMyService().login(cardNo,password,equipmentData).
       map(new HttpMethods.HttpResultFunc<MaintainUserInfoResult>());
        httpMethods.toSubscribe(observable,subscriber);
    }
}
