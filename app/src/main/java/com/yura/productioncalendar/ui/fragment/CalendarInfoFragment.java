package com.yura.productioncalendar.ui.fragment;

import com.yura.productioncalendar.R;
import com.yura.productioncalendar.databinding.FragmentCalendarInfoBinding;
import com.yura.productioncalendar.injection.component.FragmentComponent;
import com.yura.productioncalendar.utils.Utils;

public class CalendarInfoFragment extends BaseFragment<FragmentCalendarInfoBinding> {

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_calendar_info;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected void init() {

    }

    public void showDayInfo(int total, int work, int weekends) {
        binding.tvDayInfo.setText(String.format("Всего дней - %s, рабочих - %s, выходных/праздничных - %s", total, work, weekends));
    }

    public void showHourInfo(int h40, double h36, double h24) {
        binding.tvHoursInfo.setText(String.format("Кол-во часов при 40-час недели - %s, "
                        + "36 часовой недели - %s, "
                        + "24-х часовой недели - %s",
                        h40, Utils.round(h36, 1), Utils.round(h24, 1)));
    }

    public void showMountInfo(int quarter, int half, int year) {
        binding.tvMonthInfo.setText(String.format("Текущий квартал - %s, полугодие - %s, год - %s", quarter, half, year));
    }
}
