package com.yura.productioncalendar.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.yura.productioncalendar.CalendarApplication;
import com.yura.productioncalendar.injection.component.ConfigPersistentComponent;
import com.yura.productioncalendar.injection.component.DaggerConfigPersistentComponent;
import com.yura.productioncalendar.injection.component.FragmentComponent;
import com.yura.productioncalendar.injection.module.FragmentModule;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseFragment<B extends ViewDataBinding> extends MvpAppCompatFragment {

    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    protected B binding;
    private static LongSparseArray<ConfigPersistentComponent> componentsArray =
            new LongSparseArray<>();
    private static final AtomicLong NEXT_ID = new AtomicLong(0);

    private long fragmentId;

    protected FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        fragmentId =
                savedInstanceState != null
                        ? savedInstanceState.getLong(KEY_FRAGMENT_ID)
                        : NEXT_ID.getAndIncrement();

        ConfigPersistentComponent configPersistentComponent;
        if (componentsArray.get(fragmentId) == null) {
            configPersistentComponent =
                    DaggerConfigPersistentComponent.builder()
                            .appComponent(CalendarApplication.getComponent())
                            .build();
            componentsArray.put(fragmentId, configPersistentComponent);
        } else {
            configPersistentComponent = componentsArray.get(fragmentId);
        }

        mFragmentComponent =
                configPersistentComponent.fragmentComponent(new FragmentModule(this));
        inject(mFragmentComponent);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getFragmentLayout(), container, false);
        init();
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_FRAGMENT_ID, fragmentId);
    }

    @Override
    public void onDestroy() {
        if (!getActivity().isChangingConfigurations()) {
            componentsArray.remove(fragmentId);
        }
        super.onDestroy();
    }

    protected abstract int getFragmentLayout();

    protected abstract void inject(FragmentComponent fragmentComponent);

    protected abstract void init();

}
