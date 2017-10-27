package com.yura.productioncalendar.injection.component;

import android.app.Application;
import android.content.Context;

import com.yura.productioncalendar.api.DataManager;
import com.yura.productioncalendar.injection.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    //@ApplicationContext
    Context context();

    Application application();

    DataManager apiManager();

}

