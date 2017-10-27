package com.yura.productioncalendar.presentation.view;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;

public interface MainActivityView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showProgressBar(boolean show);

    @StateStrategyType(SkipStrategy.class)
    void showInfo(Calendar minimumDate,
                  Calendar maximumDate,
                  ArrayList<CalendarDay> weekends,
                  ArrayList<CalendarDay> holidays);

    @StateStrategyType(SkipStrategy.class)
    void showDayInfo(int total, int word, int weekends);

    @StateStrategyType(SkipStrategy.class)
    void showHourInfo(int h40, double h36, double h24);

    @StateStrategyType(SkipStrategy.class)
    void showMountInfo(int quarter, int half, int year);

}
