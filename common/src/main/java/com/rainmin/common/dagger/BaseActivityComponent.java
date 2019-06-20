package com.rainmin.common.dagger;

import com.rainmin.common.base.BaseMvpActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<BaseMvpActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseMvpActivity> {}
}
