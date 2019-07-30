package com.alacritystudios.encore.dependency.module

import android.content.ContentResolver
import com.alacritystudios.encore.data.repository.MediaRepository
import com.alacritystudios.encore.util.AppExecutors
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesMediaRepository(contentResolver: ContentResolver, appExecutors: AppExecutors): MediaRepository {
        return MediaRepository(contentResolver, appExecutors)
    }
}
