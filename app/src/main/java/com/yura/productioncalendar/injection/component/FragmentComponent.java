package com.yura.productioncalendar.injection.component;

import android.content.Context;

import com.yura.productioncalendar.api.DataManager;
import com.yura.productioncalendar.injection.PerFragment;
import com.yura.productioncalendar.injection.module.FragmentModule;
import com.yura.productioncalendar.ui.fragment.CalendarFragment;
import com.yura.productioncalendar.ui.fragment.CalendarInfoFragment;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(CalendarFragment calendarFragment);
    void inject(CalendarInfoFragment calendarInfoFragment);

    DataManager dataManager();

    Context context();
}
