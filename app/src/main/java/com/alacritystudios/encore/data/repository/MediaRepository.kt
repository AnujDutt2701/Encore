package com.alacritystudios.encore.data.repository

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import com.alacritystudios.encore.util.CursorConstants
import com.alacritystudios.encore.MediaStoreResource
import com.alacritystudios.encore.SortOrder
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.util.AppExecutors
import com.alacritystudios.encore.util.ContentProviderResponseUtil
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MediaRepository {

    var contentResolver: ContentResolver
    var appExecutors: AppExecutors

    constructor(contentResolver: ContentResolver, appExecutors: AppExecutors) {
        this.contentResolver = contentResolver
        this.appExecutors = appExecutors
    }

    public fun fetchAllArtists(): LiveData<ContentProviderResponseUtil<List<MediaItem>>> {

        val mediaStoreResource: MediaStoreResource<List<MediaItem>> =
            object : MediaStoreResource<List<MediaItem>>(contentResolver, appExecutors) {

                override fun returnEmptyCall(): List<MediaItem> {
                    var temp: List<MediaItem> = arrayListOf()
                    return temp
                }

                override fun processCursor(cursor: Cursor?): ContentProviderResponseUtil<List<MediaItem>> {
                    var artistsList = ArrayList<MediaItem>()
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                artistsList.add(MediaItem.formatCursorToArtist(cursor))
//                                val isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC))
//                                val duration = cursor.getLong(CursorConstants.DURATION_COLUMN)
//                                if (isMusic != 0 && duration > 0) {
//                                    artistsList.add(MediaItem.formatCursorToArtist(cursor))
//                                }
                            } while (cursor.moveToNext())

                            return ContentProviderResponseUtil.createSuccessResponse(
                                ArrayList<MediaItem>(),
                                artistsList
                            )
                        } else {
                            return ContentProviderResponseUtil.createSuccessResponse(ArrayList<MediaItem>(), null)
                        }
                    } catch (ex: Exception) {
                        return ContentProviderResponseUtil.createFailureResponse(ex)
                    }
                }

                override fun queryContentProvider(): Cursor? {
                    val projection = arrayOf(
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST,
                        MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                        MediaStore.Audio.Artists.NUMBER_OF_TRACKS
                    )

                    return this.contentResolver.query(
                        MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null
                    )
                }
            }
        return mediaStoreResource.returnAsLiveData()
    }

    public fun fetchAllAlbums(): LiveData<ContentProviderResponseUtil<List<MediaItem>>> {

        val mediaStoreResource: MediaStoreResource<List<MediaItem>> =
            object : MediaStoreResource<List<MediaItem>>(contentResolver, appExecutors) {

                override fun returnEmptyCall(): List<MediaItem> {
                    var temp: List<MediaItem> = arrayListOf()
                    return temp
                }

                override fun processCursor(cursor: Cursor?): ContentProviderResponseUtil<List<MediaItem>> {
                    var albumsList = ArrayList<MediaItem>()
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                albumsList.add(MediaItem.formatCursorToAlbum(cursor))
//                                val isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC))
//                                val duration = cursor.getLong(CursorConstants.DURATION_COLUMN)
//                                if (isMusic != 0 && duration > 0) {
//                                    artistsList.add(MediaItem.formatCursorToArtist(cursor))
//                                }
                            } while (cursor.moveToNext())

                            return ContentProviderResponseUtil.createSuccessResponse(
                                ArrayList<MediaItem>(),
                                albumsList
                            )
                        } else {
                            return ContentProviderResponseUtil.createSuccessResponse(ArrayList<MediaItem>(), null)
                        }
                    } catch (ex: Exception) {
                        return ContentProviderResponseUtil.createFailureResponse(ex)
                    }
                }

                override fun queryContentProvider(): Cursor? {
                    val projection = arrayOf(
                        MediaStore.Audio.Albums._ID,
                        MediaStore.Audio.Albums.ALBUM,
                        MediaStore.Audio.Albums.ALBUM_ART,
                        MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.NUMBER_OF_SONGS
                    )

                    return this.contentResolver.query(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null
                    )
                }
            }
        return mediaStoreResource.returnAsLiveData()
    }

    public fun fetchAllSongs(): LiveData<ContentProviderResponseUtil<List<MediaItem>>> {

        val mediaStoreResource: MediaStoreResource<List<MediaItem>> =
            object : MediaStoreResource<List<MediaItem>>(contentResolver, appExecutors) {

                override fun returnEmptyCall(): List<MediaItem> {
                    var temp: List<MediaItem> = arrayListOf()
                    return temp
                }

                override fun processCursor(cursor: Cursor?): ContentProviderResponseUtil<List<MediaItem>> {
                    var songsList = ArrayList<MediaItem>()
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                songsList.add(MediaItem.formatCursorToSong(cursor))
                            } while (cursor.moveToNext())

                            return ContentProviderResponseUtil.createSuccessResponse(
                                ArrayList<MediaItem>(),
                                songsList
                            )
                        } else {
                            return ContentProviderResponseUtil.createSuccessResponse(ArrayList<MediaItem>(), null)
                        }
                    } catch (ex: Exception) {
                        return ContentProviderResponseUtil.createFailureResponse(ex)
                    }
                }

                override fun queryContentProvider(): Cursor? {
                    val projection = arrayOf(
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ALBUM_ID
                    )

                    val selection = MediaStore.Audio.Media.IS_MUSIC + " IS NOT 0 AND " + MediaStore.Audio.Media.DURATION + "> 0"

                    return this.contentResolver.query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        null,
                        null
                    )
                }
            }
        return mediaStoreResource.returnAsLiveData()
    }

    public fun fetchAllRecentlyAddedSongs(): LiveData<ContentProviderResponseUtil<List<MediaItem>>> {

        val mediaStoreResource: MediaStoreResource<List<MediaItem>> =
            object : MediaStoreResource<List<MediaItem>>(contentResolver, appExecutors) {

                override fun returnEmptyCall(): List<MediaItem> {
                    var temp: List<MediaItem> = arrayListOf()
                    return temp
                }

                override fun processCursor(cursor: Cursor?): ContentProviderResponseUtil<List<MediaItem>> {
                    var songsList = ArrayList<MediaItem>()
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            do {

                                songsList.add(MediaItem.formatCursorToSong(cursor))
                            } while (cursor.moveToNext())

                            return ContentProviderResponseUtil.createSuccessResponse(
                                ArrayList<MediaItem>(),
                                songsList
                            )
                        } else {
                            return ContentProviderResponseUtil.createSuccessResponse(ArrayList<MediaItem>(), null)
                        }
                    } catch (ex: Exception) {
                        return ContentProviderResponseUtil.createFailureResponse(ex)
                    }
                }

                override fun queryContentProvider(): Cursor? {
                    val projection = arrayOf(
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.DATE_ADDED
                    )

                    val selection = MediaStore.Audio.Media.DATE_ADDED + ">? AND " + MediaStore.Audio.Media.IS_MUSIC + " IS NOT 0 AND " + MediaStore.Audio.Media.DURATION + "> 0"

                    val selectionArgs = arrayOf(
                        ((System.currentTimeMillis()/1000) - TimeUnit.SECONDS.convert(7, TimeUnit.DAYS)).toString()
                    )

                    return this.contentResolver.query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        null
                    )
                }
            }
        return mediaStoreResource.returnAsLiveData()
    }

    public fun processSortOrder(sortOrder: SortOrder): String {
        when (sortOrder) {
            SortOrder.SORT_ORDER_MEDIA_NAME_ASCENDING ->
                return MediaStore.EXTRA_MEDIA_TITLE
            SortOrder.SORT_ORDER_MEDIA_NAME_DESCENDING ->
                return MediaStore.EXTRA_MEDIA_TITLE
        }
    }
}