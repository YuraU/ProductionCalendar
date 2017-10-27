package com.yura.productioncalendar.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.util.LongSparseArray;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.yura.productioncalendar.CalendarApplication;
import com.yura.productioncalendar.injection.component.ActivityComponent;
import com.yura.productioncalendar.injection.component.ConfigPersistentComponent;
import com.yura.productioncalendar.injection.component.DaggerConfigPersistentComponent;
import com.yura.productioncalendar.injection.module.ActivityModule;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseActivity<B extends ViewDataBinding> extends MvpAppCompatActivity {

    protected B binding;
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static LongSparseArray<ConfigPersistentComponent> componentsArray =
            new LongSparseArray<>();

    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long activityId =
                savedInstanceState != null
                        ? savedInstanceState.getLong(KEY_ACTIVITY_ID)
                        : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (componentsArray.get(activityId) == null) {
            configPersistentComponent =
                    DaggerConfigPersistentComponent.builder()
                            .appComponent(CalendarApplication.getComponent())
                            .build();
            componentsArray.put(activityId, configPersistentComponent);
        } else {
            configPersistentComponent = componentsArray.get(activityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
        inject(mActivityComponent);

        super.onCreate(savedInstanceState);
        bindView(getLayout());
    }

    protected abstract int getLayout();

    private void bindView(int layout) {
        binding = DataBindingUtil.setContentView(this, layout);
    }

    protected abstract void inject(ActivityComponent activityComponent);

}
