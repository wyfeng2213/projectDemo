package com.cmcc.patient.httprequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.cmcc.requestlibrary.bean.HttpResult;
import com.cmcc.requestlibrary.subscribers.SubscriberOnNextListener;
import com.cmcc.requestlibrary.utils.EncryptUtil;
import com.cmcc.requestlibrary.utils.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * The type Http methods.
 *
 * @author shuyang on 16/3/9.
 */
public class HttpMethods {

    /**
     * The constant BASE_URL.
     */
    public static final String BASE_URL = "https://www.kuandai10086.cn:8039/aurora/";
    private static final int DEFAULT_TIMEOUT = 5;
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Retrofit retrofit;

    private MyService myService;

    public MyService getMyService() {
        return myService;
    }

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        // 添加证书
        List<InputStream> certificates = new ArrayList<>();
        List<byte[]> certs_data = NetConfig.getCertificatesData();
        // 将字节数组转为数组输入流
        if (certs_data != null && !certs_data.isEmpty()) {
            for (byte[] bytes:certs_data) {
                certificates.add(new ByteArrayInputStream(bytes));
            }
        }
        SSLSocketFactory sslSocketFactory = getSocketFactory(certificates);
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory);
        }
        //添加打印请求到的json字符串和查看log
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        myService = retrofit.create(MyService.class);
    }

    /**
     * 添加证书
     *
     * @param certificates
     */
    private static SSLSocketFactory getSocketFactory(List<InputStream> certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
            keyStore.load(null);
            try {
                for (int i = 0, size = certificates.size(); i < size; ) {
                    InputStream certificate = certificates.get(i);
                    String certificateAlias = Integer.toString(i++);
                    keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                    if (certificate != null)
                        certificate.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCode() != 200) {
                throw new ApiException(103);
            }
            return httpResult.getData();
        }
    }
    public  <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }



    /**
     * 获取本机基本信息
     *
     * @param context the context
     * @return local info
     */
    public  String getLocalInfo(Context context) {
        JSONObject jsonObject = new JSONObject();
        SharedPreferences preferences = context.getSharedPreferences("aurora", 0);
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        String imsi = mTelephonyMgr.getSubscriberId();
//        String imei = mTelephonyMgr.getDeviceId();
        try {
            if ("null".equals(preferences.getString("telphone", "null"))) {
                String telphoneLocal = preferences.getString("telphoneLocal", "null");
                if(!"null".equals(telphoneLocal)){
                    telphoneLocal =telphoneLocal;
                }
                jsonObject.put("hPhonenumber",telphoneLocal);
            } else {
                String telphone = preferences.getString("telphone", "null");
                if(!"null".equals(telphone)){
                    telphone = telphone;
                }
                jsonObject.put("hPhonenumber",telphone);
            }
//            jsonObject.put("hDeviceToken", UmengRegistrar.getRegistrationId(context));
            String session= EncryptUtil.decode(preferences.getString("session",""));
            jsonObject.put("session",session);
            String sessionKey=EncryptUtil.decode(preferences.getString("sessionKey",""));
            jsonObject.put("sessionKey",sessionKey);
            jsonObject.put("hSystemtype", "android" + Build.VERSION.RELEASE);
            jsonObject.put("hmodel", Build.BRAND + " " + Build.MODEL);
            String hCity = preferences.getString("hCity", "null");
            if(!"null".equals(hCity)){
                hCity =hCity;
            }
            jsonObject.put("hCity",hCity);
            PackageManager manager = context.getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(context.getPackageName(), 0);
            if (info != null) {
                jsonObject.put("hpackageVesion", info.versionName);
            } else {
                jsonObject.put("hpackageVesion", "V1.1.6.151231");
            }
            jsonObject.put("hImsi", mTelephonyMgr.getSubscriberId());
            jsonObject.put("hImei", mTelephonyMgr.getDeviceId());
        } catch (Exception e) {
            try {
                jsonObject.put("hImsi", "android6受限");
                jsonObject.put("hImei", "android6受限");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
