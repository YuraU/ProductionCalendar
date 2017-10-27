package com.yura.productioncalendar.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.yura.productioncalendar.R;
import com.yura.productioncalendar.databinding.FragmentCalendarBinding;
import com.yura.productioncalendar.injection.component.FragmentComponent;
import com.yura.productioncalendar.utils.DayDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends BaseFragment<FragmentCalendarBinding> {

    private Listener mListener;

    private CalendarDay curVisibleDay;

    public static CalendarFragment newInstance(Calendar minimumDate,
                                               Calendar maximumDate,
                                               ArrayList<CalendarDay> weekends,
                                               ArrayList<CalendarDay> holidays) {
        final CalendarFragment fragment = new CalendarFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable("minimumDate", minimumDate);
        arguments.putSerializable("maximumDate", maximumDate);
        arguments.putParcelableArrayList("weekends", weekends);
        arguments.putParcelableArrayList("holidays", holidays);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Activity activity = (Activity) context;
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            Calendar minimumDate = (Calendar) getArguments().getSerializable("minimumDate");
            Calendar maximumDate = (Calendar) getArguments().getSerializable("maximumDate");
            ArrayList<CalendarDay> weekends = getArguments().getParcelableArrayList("weekends");
            ArrayList<CalendarDay> holidays = getArguments().getParcelableArrayList("holidays");

            curVisibleDay = getArguments().getParcelable("curVisibleDay");

            binding.calendarView.state().edit()
                    .setFirstDayOfWeek(Calendar.MONDAY)
                    .setMinimumDate(minimumDate)
                    .setMaximumDate(maximumDate)
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit();

            binding.calendarView.addDecorator(new DayDecorator(Color.parseColor("#F08080"), weekends));
            binding.calendarView.addDecorator(new DayDecorator(Color.YELLOW, holidays));

            binding.calendarView.setOnMonthChangedListener((widget, date) -> {
                curVisibleDay = date;
                mListener.onMonthChanged(date.getYear(), date.getMonth());
            });

            if (curVisibleDay == null) {
                curVisibleDay = CalendarDay.from(new Date());
            }

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mListener.onMonthChanged(curVisibleDay.getYear(), curVisibleDay.getMonth());
                }
            });
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("curVisibleDay", curVisibleDay);
    }

    public interface Listener {
        void onMonthChanged(int year, int month);
    }

}
