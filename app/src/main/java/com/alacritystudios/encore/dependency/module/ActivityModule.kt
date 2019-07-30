package com.alacritystudios.vortex.dependency.module

import com.alacritystudios.encore.ui.activity.HomeActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun homeActivity(): HomeActivity
}