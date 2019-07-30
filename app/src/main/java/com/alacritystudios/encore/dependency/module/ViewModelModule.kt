package com.alacritystudios.encore.dependency.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alacritystudios.encore.dependency.key.ViewModelKey
import com.alacritystudios.encore.ui.EncoreViewModelFactory
import com.alacritystudios.encore.ui.fragment.home.HomeViewModel
import com.alacritystudios.encore.ui.fragment.albums.AlbumsViewModel
import com.alacritystudios.encore.ui.fragment.artists.ArtistsViewModel
import com.alacritystudios.encore.ui.fragment.songs.SongsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: EncoreViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(SongsViewModel::class)
    abstract fun contributesSongsViewModel(songsViewModel: SongsViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(ArtistsViewModel::class)
    abstract fun contributesArtistsViewModel(artistsViewModel: ArtistsViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(AlbumsViewModel::class)
    abstract fun contributesAlbumsViewModel(albumsViewModel: AlbumsViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    abstract fun contributesHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}
