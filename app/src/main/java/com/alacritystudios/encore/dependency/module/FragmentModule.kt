package com.alacritystudios.encore.dependency.module


import com.alacritystudios.encore.ui.fragment.home.HomeFragment
import com.alacritystudios.encore.ui.fragment.albums.AlbumsFragment
import com.alacritystudios.encore.ui.fragment.artists.ArtistsFragment
import com.alacritystudios.encore.ui.fragment.songs.SongsFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun songsFragment(): SongsFragment

    @ContributesAndroidInjector
    abstract fun albumsFragment(): AlbumsFragment

    @ContributesAndroidInjector
    abstract fun artistsFragment(): ArtistsFragment

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment
}
