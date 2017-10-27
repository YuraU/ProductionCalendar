package com.yura.productioncalendar.presentation.presenter;

import android.content.Context;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.yura.productioncalendar.api.DataManager;
import com.yura.productioncalendar.model.CalendarInfo;
import com.yura.productioncalendar.presentation.view.MainActivityView;
import com.yura.productioncalendar.ui.fragment.CalendarFragment;
import com.yura.productioncalendar.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> implements CalendarFragment.Listener {

    private final Context context;
    private final DataManager dataManager;

    private final List<CalendarInfo> calendarInfoList;

    public MainActivityPresenter(Context context, DataManager dataManager) {
        this.context = context;
        this.dataManager = dataManager;

        calendarInfoList = new ArrayList<>();
    }

    public void getCallInfo() {
        if (!calendarInfoList.isEmpty()) {
            return;
        }

        getViewState().showProgressBar(true);
        dataManager
                .getCalendarInfo()
                .compose(Utils.ioToMain())
                .subscribe(
                        calendarInfos -> {
                            calendarInfoList.clear();
                            calendarInfoList.addAll(calendarInfos);
                            getViewState().showProgressBar(false);

                            getViewState().showInfo(getMinimumDate(), getMaximumDate(), getWeekends(), getHolidays());
                        },
                        throwable -> {
                            getViewState().showProgressBar(false);
                        });
    }

    public Calendar getMinimumDate() {
        return calendarInfoList.get(0).getFirstDay();
    }

    public Calendar getMaximumDate() {
        return calendarInfoList.get(calendarInfoList.size() - 1).getLastDay();
    }

    public ArrayList<CalendarDay> getWeekends() {
        return (ArrayList) Stream.of(calendarInfoList)
                            .flatMap(new Function<CalendarInfo, Stream<CalendarDay>>() {
                                    @Override
                                    public Stream<CalendarDay> apply(CalendarInfo calendarInfo) {
                                        return Stream.of(calendarInfo.getWeekendDays());
                                    }
                            })
                            .toList();
    }

    public ArrayList<CalendarDay> getHolidays() {
        return (ArrayList) Stream.of(calendarInfoList)
                            .flatMap(new Function<CalendarInfo, Stream<CalendarDay>>() {
                                @Override
                                public Stream<CalendarDay> apply(CalendarInfo calendarInfo) {
                                    return Stream.of(calendarInfo.getHolidays());
                                }
                            })
                            .toList();
    }

    @Override
    public void onMonthChanged(int year, int month) {
        CalendarInfo calendarInfo = Stream.of(calendarInfoList)
                                        .filter(f -> f.getYear().equals(String.valueOf(year)))
                                        .single();

        int weekends = calendarInfo.getWeekendsInMonth(year, month).size();
        Calendar mycal = new GregorianCalendar(year, month, 1);
        int total = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int work = total - weekends;

        getViewState().showDayInfo(total, work, weekends);
        //CHECKSTYLE:OFF
        getViewState().showHourInfo(8 * work, 7.2 * work, 4.8 * work);
        getViewState().showMountInfo(month / 3 + 1, month / 6 + 1, year);
        //CHECKSTYLE:ON
    }
}
