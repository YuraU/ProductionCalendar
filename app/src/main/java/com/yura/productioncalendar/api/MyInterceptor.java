package com.yura.productioncalendar.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yura.productioncalendar.utils.Utils;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class MyInterceptor implements Interceptor {

    private static final int REPEAT_TIME = 3;

    private final Context context;

    @Inject
    public MyInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!Utils.isNetworkConnected(context)) {
            throw new RuntimeException("No internet");
        }

        Request originalRequest = chain.request();

        Response response = null;

        for (int i = 0; i < REPEAT_TIME; i++) {
            response = chain.proceed(originalRequest);

            if (response.isSuccessful()) {
                return response;
            } else {
                //Try again
            }
        }

        return null;
        //throw new HttpException(response);
    }
}
