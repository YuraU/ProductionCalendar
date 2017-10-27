package com.yura.productioncalendar.injection.module;

import com.yura.productioncalendar.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @Singleton
    ApiService provideLineBApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
