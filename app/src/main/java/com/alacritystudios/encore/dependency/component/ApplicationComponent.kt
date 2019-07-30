package com.alacritystudios.encore.dependency.component

import com.alacritystudios.encore.application.EncoreApplication
import com.alacritystudios.encore.dependency.module.*
import com.alacritystudios.encore.dependency.scope.EncoreScope
import com.alacritystudios.vortex.dependency.module.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@EncoreScope
@Component(modules = [AndroidSupportInjectionModule::class, AndroidInjectionModule::class, AppModule::class, ActivityModule::class, FragmentModule::class, ViewModelModule::class, RepositoryModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun inject(application: EncoreApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun encoreApplication(application: EncoreApplication): Builder

        fun build(): ApplicationComponent
    }
}
