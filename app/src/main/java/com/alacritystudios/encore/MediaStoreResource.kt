package com.alacritystudios.encore

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alacritystudios.encore.util.AppExecutors
import com.alacritystudios.encore.util.ContentProviderResponseUtil

public abstract class MediaStoreResource<OutputObservableType> {

    protected var result: MutableLiveData<ContentProviderResponseUtil<OutputObservableType>>
    protected var contentResolver: ContentResolver
    protected var appExecutors: AppExecutors

    protected val musicURI: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    constructor(contentResolver: ContentResolver, appExecutors: AppExecutors) {
        this.contentResolver = contentResolver
        this.appExecutors = appExecutors
        this.result = MutableLiveData()
        executeCall()
    }

    @MainThread
    public fun executeCall() {
        appExecutors.diskIO().execute(Runnable {
            result.postValue(ContentProviderResponseUtil.createLoadingResponse(returnEmptyCall()))
            result.postValue(processCursor(queryContentProvider()))
        })
    }

    @MainThread
    public abstract fun returnEmptyCall(): OutputObservableType

    @WorkerThread
    public abstract fun processCursor(cursor: Cursor?): ContentProviderResponseUtil<OutputObservableType>

    @WorkerThread
    public abstract fun queryContentProvider(): Cursor?

    @WorkerThread
    public fun returnAsLiveData(): LiveData<ContentProviderResponseUtil<OutputObservableType>> {
        return result
    }
}