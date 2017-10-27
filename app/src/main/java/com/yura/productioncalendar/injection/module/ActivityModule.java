package com.yura.productioncalendar.injection.module;

import android.app.Activity;
import android.content.Context;

import com.yura.productioncalendar.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return activity.getApplicationContext();
    }

}
