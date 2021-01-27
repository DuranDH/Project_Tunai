package com.hw.pq.model.http;

import com.hw.pq.model.bean.BaseModel;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    // 测试
//    String HOST = "http://app.gzmeihao2.com/";
    // 正式
    String HOST = "http://fqklapp.gzmeihao2.com/";


    // 通用接口

    /**
     * 短信验证码
     * @param data
     * @return
     */
    @POST("info/verifyCode")
    Observable<BaseModel> getVerifyCode(@Body String data);

}
