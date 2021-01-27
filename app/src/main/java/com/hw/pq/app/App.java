package com.hw.pq.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.hw.pq.BuildConfig;
import com.hw.pq.di.component.AppComponent;
import com.hw.pq.di.component.DaggerAppComponent;
import com.hw.pq.di.module.AppModule;
import com.hw.pq.util.SPUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by codeest on 2016/8/2.
 */
public class App extends Application {

    private static App instance;
    private Set<Activity> allActivities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static synchronized App getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化屏幕宽高
        getScreenSize();

        //初始化Log
        KLog.init(BuildConfig.DEBUG, "base_project");

//        AutoSizeConfig.getInstance().getUnitsManager()
//                .setSupportDP(false)
//                .setSupportSP(false)
//                .setSupportSubunits(Subunits.MM);

        //初始化错误收集
//        CrashHandler.init(new CrashHandler(getApplicationContext()));
//
//        //初始化内存泄漏检测
//        LeakCanary.install(this);

    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void removeAllActivities() {
        if (allActivities != null) {
            for (Activity activity : allActivities) {
                activity.finish();
            }
            allActivities.clear();
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void exitToLogin() {
//        if (!"default".equals(App.getInstance().getToken())) {
//            setToken("default");
//            setMobile("default");
//            setStatus(-100);
//            removeAllActivities();
//            Intent i = new Intent(App.getInstance(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//        }
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

    public void setToken(String token) {
        SPUtils.put(this,"token", token);
    }

    public String getToken() {
        return (String) SPUtils.get(this, "token", "default");
    }

}
