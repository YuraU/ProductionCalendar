package com.yura.productioncalendar;


import android.app.Application;

import com.facebook.stetho.Stetho;
import com.singhajit.sherlock.core.Sherlock;
import com.squareup.leakcanary.LeakCanary;
import com.tspoon.traceur.Traceur;
import com.yura.productioncalendar.injection.component.AppComponent;
import com.yura.productioncalendar.injection.component.DaggerAppComponent;
import com.yura.productioncalendar.injection.module.AppModule;
import com.yura.productioncalendar.injection.module.NetworkModule;

public class CalendarApplication extends Application {

    private AppComponent mAppComponent;
    private static CalendarApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        saveApp(this);

        initDependency();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
            Sherlock.init(this);
            Traceur.enableLogging();
        }
    }

    private static void initDependency() {
        sApplication.mAppComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(sApplication, BuildConfig.API_ENDPOINT))
                .appModule(new AppModule(sApplication))
                .build();
    }

    public static AppComponent getComponent() {
        return sApplication.mAppComponent;
    }

    private static void saveApp(CalendarApplication app) {
        synchronized (CalendarApplication.class) {
            sApplication = app;
        }
    }

}
