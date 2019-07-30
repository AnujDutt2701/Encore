package com.alacritystudios.encore.dependency.module

import android.content.ContentResolver
import android.content.Context
import com.alacritystudios.encore.application.EncoreApplication
import com.alacritystudios.encore.ui.binding.BindingComponent
import com.alacritystudios.encore.util.AppExecutors
import com.alacritystudios.encore.util.PreferenceUtil
import com.alacritystudios.encore.util.ThemeUtil

import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun providesApplicationContext(): Context {
        return EncoreApplication.getEncoreApplicationInstance().getApplicationContext()
    }

    @Provides
    fun providesAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    fun providesContentResolver(context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun providesPreferenceUtil(context: Context): PreferenceUtil {
        return PreferenceUtil(context)
    }

    @Provides
    fun providesThemeUtil(preferenceUtil: PreferenceUtil): ThemeUtil {
        return ThemeUtil(preferenceUtil)
    }

    @Provides
    fun providesDataBindingComponent(): BindingComponent {
        return BindingComponent()
    }
}
