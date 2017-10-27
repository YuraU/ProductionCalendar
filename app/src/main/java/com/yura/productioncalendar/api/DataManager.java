package com.yura.productioncalendar.api;

import android.content.Context;

import com.yura.productioncalendar.model.CalendarInfo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DataManager {

    @Inject
    ApiService service;
    @Inject
    Context context;

    @Inject
    public DataManager() {
    }

    public Single<List<CalendarInfo>> getCalendarInfo() {
        return service.getCalendarInfo(API.TOKEN)
                .onErrorReturn(throwable -> {
                    return null;
                })
                .singleOrError();
    }

}
