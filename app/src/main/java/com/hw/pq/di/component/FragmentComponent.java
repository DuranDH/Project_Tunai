package com.hw.pq.di.component;

import android.app.Activity;

import com.hw.pq.di.FragmentScope;
import com.hw.pq.di.module.FragmentModule;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();
//    void inject(IndexFragment indexFragment);
}
