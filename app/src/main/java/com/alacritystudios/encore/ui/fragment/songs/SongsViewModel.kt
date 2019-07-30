package com.alacritystudios.encore.ui.fragment.songs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel;
import com.alacritystudios.encore.Song
import com.alacritystudios.encore.SortOrder
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.data.repository.MediaRepository
import com.alacritystudios.encore.util.ContentProviderResponseUtil
import timber.log.Timber
import javax.inject.Inject

class SongsViewModel : ViewModel {
    // TODO: Implement the ViewModel

    private var mediaRepository: MediaRepository
    private var sortOrderLiveData: MutableLiveData<SortOrder>
    private var songsLiveData: LiveData<ContentProviderResponseUtil<List<MediaItem>>>

    @Inject
    constructor(mediaRepository: MediaRepository) : super() {
        this.mediaRepository = mediaRepository
        this.sortOrderLiveData = MutableLiveData()
        this.songsLiveData = Transformations.switchMap(this.sortOrderLiveData, {
            this.mediaRepository.fetchAllSongs()
        })
    }

    fun refresh() {
        Timber.d("refresh()")
        sortOrderLiveData.setValue(sortOrderLiveData.getValue())
    }

    fun updateSortOrder(sortOrder: SortOrder) {
        Timber.d("updateSortOrder()")
        sortOrderLiveData.setValue(sortOrder)
    }

    fun resultsLiveData() : LiveData<ContentProviderResponseUtil<List<MediaItem>>>{
        Timber.d("resultsLiveData()")
        return songsLiveData
    }
}
