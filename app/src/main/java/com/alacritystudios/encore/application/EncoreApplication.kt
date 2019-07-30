package com.alacritystudios.encore.application

import android.app.Activity
import android.app.Application
import android.app.Service
import com.alacritystudios.encore.dependency.component.ApplicationComponent
import com.alacritystudios.encore.dependency.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import timber.log.Timber
import javax.inject.Inject

class EncoreApplication : Application, HasActivityInjector, HasServiceInjector {

    @Inject
    internal lateinit var activityAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    internal lateinit var serviceAndroidInjector: DispatchingAndroidInjector<Service>

    private lateinit var applicationComponent: ApplicationComponent

    constructor()

    constructor(
        activityAndroidInjector: DispatchingAndroidInjector<Activity>,
        serviceAndroidInjector: DispatchingAndroidInjector<Service>
    ) : super() {
        this.activityAndroidInjector = activityAndroidInjector
        this.serviceAndroidInjector = serviceAndroidInjector
    }

    companion object {
        internal lateinit var encoreApplication: EncoreApplication
        fun getEncoreApplicationInstance(): EncoreApplication = encoreApplication
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        encoreApplication = this;
        applicationComponent = DaggerApplicationComponent.builder().encoreApplication(this).build();
        applicationComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return serviceAndroidInjector
    }
}