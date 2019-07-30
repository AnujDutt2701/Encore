package com.alacritystudios.encore.data.domain

import android.database.Cursor
import com.alacritystudios.encore.util.CursorConstants
import android.content.ContentUris
import android.net.Uri


class MediaItem {

    enum class MediaItemType {
        MEDIA_TYPE_ARTIST,
        MEDIA_TYPE_ALBUM,
        MEDIA_TYPE_GENRE,
        MEDIA_TYPE_PLAYLIST,
        MEDIA_TYPE_SONG
    }

    var mediaId: Long
    var mediaDisplayUrl: String?
    var mediaTitle: String
    var mediaSubTitle: List<String>
    var mediaType: MediaItemType

    constructor(
        mediaId: Long,
        mediaDisplayUrl: String?,
        mediaTitle: String,
        mediaSubTitle: List<String>,
        mediaType: MediaItemType
    ) {
        this.mediaId = mediaId
        this.mediaDisplayUrl = mediaDisplayUrl
        this.mediaTitle = mediaTitle
        this.mediaSubTitle = mediaSubTitle
        this.mediaType = mediaType
    }


    companion object {

        fun formatCursorToArtist(cursor: Cursor): MediaItem {
            return MediaItem(
                cursor.getLong(CursorConstants.AUDIO_ARTISTS_ID),
                null,
                cursor.getString(CursorConstants.AUDIO_ARTISTS_ARTIST),
                formatArtistSubTitle(
                    cursor.getLong(CursorConstants.AUDIO_ARTISTS_NUMBER_OF_ALBUMS),
                    cursor.getLong(CursorConstants.AUDIO_ARTISTS_NUMBER_OF_TRACKS)
                ),
                MediaItemType.MEDIA_TYPE_ARTIST
            )
        }

        private fun formatArtistSubTitle(numberOfAlbums: Long, numberOfTracks: Long): List<String> {
            return arrayListOf<String>(numberOfAlbums.toString(), numberOfTracks.toString())
        }

        fun formatCursorToAlbum(cursor: Cursor): MediaItem {
            return MediaItem(
                cursor.getLong(CursorConstants.AUDIO_ALBUMS_ID),
                "content://media/external/audio/albumart/" +
                        cursor.getLong(CursorConstants.AUDIO_ALBUMS_ID),
                cursor.getString(CursorConstants.AUDIO_ALBUMS_ALBUM),
                formatAlbumSubTitle(
                    cursor.getString(CursorConstants.AUDIO_ALBUMS_ARTIST),
                    cursor.getLong(CursorConstants.AUDIO_ALBUMS_NUMBER_OF_SONGS)
                ),
                MediaItemType.MEDIA_TYPE_ALBUM
            )
        }

        private fun formatAlbumSubTitle(artist: String, numberOfSongs: Long): List<String> {
            return arrayListOf<String>(artist, numberOfSongs.toString())
        }

        fun formatCursorToSong(cursor: Cursor): MediaItem {
            return MediaItem(
                cursor.getLong(CursorConstants.AUDIO_MEDIA_ID),
                "content://media/external/audio/albumart/" +
                        cursor.getLong(CursorConstants.AUDIO_MEDIA_ALBUM_ID),
                cursor.getString(CursorConstants.AUDIO_MEDIA_TITLE),
                formatSongSubTitle(
                    cursor.getString(CursorConstants.AUDIO_MEDIA_ARTIST),
                    cursor.getString(CursorConstants.AUDIO_MEDIA_ALBUM),
                    cursor.getLong(CursorConstants.AUDIO_MEDIA_DURATION)
                ),
                MediaItemType.MEDIA_TYPE_SONG
            )
        }

        private fun formatSongSubTitle(numberOfAlbums: String, numberOfTracks: String, duration: Long): List<String> {
            return arrayListOf<String>(numberOfAlbums, numberOfTracks, duration.toString())
        }
    }
}