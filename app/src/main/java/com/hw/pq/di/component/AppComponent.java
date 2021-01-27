package com.hw.pq.di.component;


import com.hw.pq.app.App;
import com.hw.pq.di.ContextLife;
import com.hw.pq.di.module.AppModule;
import com.hw.pq.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

}
