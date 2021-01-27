package com.hw.pq.model.http.exception;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by xiejian on 2017/6/29.
 */

public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {

    @Override
    public Observable<T> call(Throwable throwable) {
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
