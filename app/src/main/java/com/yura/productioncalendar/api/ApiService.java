package com.yura.productioncalendar.api;

import com.yura.productioncalendar.model.CalendarInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(API.URL_PRODUCTION_CALENDAR)
    Observable<List<CalendarInfo>> getCalendarInfo(@Query("access_token") String token);

}
