package com.hw.pq.model.http.exception;

import com.hw.pq.util.L;
import com.socks.library.KLog;

import rx.Subscriber;

/**
 * Created by xiejian on 2017/10/13.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {


    @Override
    public void onError(Throwable e) {

        if (e instanceof ApiException){
            KLog.d(e.toString());
            dealErrorFromNet(((ApiException)e).getCode(), ((ApiException)e).getDisplayMessage());
            onError(((ApiException)e).getDisplayMessage());
        }
    }

    protected abstract void onError(String message);

    public void dealErrorFromNet(int errorCode, String messageStr){
        L.v(L.TAG, "errorCode = " + errorCode);
        L.v(L.TAG, "messageStr = " + messageStr);
        switch (errorCode){
            case 402:

                L.v(L.TAG, "异地账号登录处理 ====== ");
                break;
        }
    }
}
