package com.hw.pq.di.component;

import android.app.Activity;

import com.hw.pq.TestActivity;
import com.hw.pq.di.ActivityScope;
import com.hw.pq.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();
    void inject(TestActivity testActivity);
}
