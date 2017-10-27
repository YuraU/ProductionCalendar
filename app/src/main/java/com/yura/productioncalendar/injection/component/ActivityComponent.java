package com.yura.productioncalendar.injection.component;

import android.content.Context;

import com.yura.productioncalendar.api.DataManager;
import com.yura.productioncalendar.injection.PerActivity;
import com.yura.productioncalendar.injection.module.ActivityModule;
import com.yura.productioncalendar.ui.activity.MainActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    DataManager dataManager();

    Context context();
}

