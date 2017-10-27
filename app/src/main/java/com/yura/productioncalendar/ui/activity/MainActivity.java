package com.yura.productioncalendar.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.yura.productioncalendar.R;
import com.yura.productioncalendar.databinding.ActivityMainBinding;
import com.yura.productioncalendar.injection.component.ActivityComponent;
import com.yura.productioncalendar.presentation.presenter.MainActivityPresenter;
import com.yura.productioncalendar.presentation.view.MainActivityView;
import com.yura.productioncalendar.ui.fragment.CalendarFragment;
import com.yura.productioncalendar.ui.fragment.CalendarInfoFragment;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements MainActivityView, CalendarFragment.Listener {

    @InjectPresenter
    MainActivityPresenter presenter;

    @ProvidePresenter(type = PresenterType.LOCAL)
    MainActivityPresenter providePresenter() {
        return new MainActivityPresenter(mActivityComponent.context(), mActivityComponent.dataManager());
    }

    private CalendarFragment calendarFragment;
    private CalendarInfoFragment calendarInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calendarFragment = (CalendarFragment) getSupportFragmentManager().findFragmentById(R.id.calendar_cont);
        calendarInfoFragment = (CalendarInfoFragment) getSupportFragmentManager().findFragmentById(R.id.calendar_info_cont);

        presenter.getCallInfo();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showInfo(Calendar minimumDate,
                         Calendar maximumDate,
                         ArrayList<CalendarDay> weekends,
                         ArrayList<CalendarDay> holidays) {

        calendarFragment = CalendarFragment.newInstance(minimumDate, maximumDate, weekends, holidays);
        calendarInfoFragment = new CalendarInfoFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_info_cont, calendarInfoFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_cont, calendarFragment)
                .commit();

    }

    @Override
    public void showDayInfo(int total, int word, int weekends) {
        calendarInfoFragment.showDayInfo(total, word, weekends);
    }

    @Override
    public void showHourInfo(int h40, double h36, double h24) {
        calendarInfoFragment.showHourInfo(h40, h36, h24);
    }

    @Override
    public void showMountInfo(int quarter, int half, int year) {
        calendarInfoFragment.showMountInfo(quarter, half, year);
    }

    @Override
    public void onMonthChanged(int year, int month) {
        presenter.onMonthChanged(year, month);
    }
}
