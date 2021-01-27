package com.hw.pq.model.http;


import android.content.Context;

import com.hw.pq.BuildConfig;
import com.hw.pq.app.App;
import com.hw.pq.app.Constants;
import com.hw.pq.util.L;
import com.hw.pq.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by codeest on 2016/8/3.
 */
public class RetrofitHelper {

    private static OkHttpClient okHttpClient = null;
    private static ApiService apiService = null;
    private static Context mContext;

    private void init() {
        initOkHttp();
        apiService = getApiService();
    }

    public RetrofitHelper(Context context) {
        this.mContext = context;
        init();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        // http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        builder.addInterceptor(new AddTokenInterceptor(mContext));

        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private static ApiService getApiService() {
        Retrofit gankRetrofit = new Retrofit.Builder()
                .baseUrl(ApiService.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return gankRetrofit.create(ApiService.class);
    }


    /* token */
    public static class AddTokenInterceptor implements Interceptor {
        private Context context;

        public AddTokenInterceptor(Context context) {
            super();
            this.context = context;

        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request.Builder builder = chain.request().newBuilder();

            if (context == null) {
                L.v(L.TAG, "context ======null ");
            }

            if (App.getInstance().getToken() == null) {

                return chain.proceed(builder.build());
            }

            builder.addHeader("token", App.getInstance().getToken());

            return chain.proceed(builder.build());
        }
    }


    // 通用接口

    /**
     * 短信验证码
     * @param data
     * @return
     */
//    public Observable<BaseModel> getVerifyCode(String data) {
//        return apiService.getVerifyCode(data);
//    }


}
