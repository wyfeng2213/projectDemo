package com.cmcc.requestlibrary.http;

import com.cmcc.requestlibrary.bean.HttpResult;
import com.cmcc.requestlibrary.bean.MaintainUserInfoResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * The interface My service.
 *
 * @author shuyang on 2016/10/20.
 */
public interface MyService {
    @FormUrlEncoded
    @POST("api/maintainUser/login.htm")
    Observable<HttpResult<MaintainUserInfoResult>> login(@Field("cardNo")String cardNo,
                                                         @Field("password")String password, @Field("equipmentData")String equipmentData);
}
