package com.alacritystudios.encore

import android.database.Cursor
import com.alacritystudios.encore.util.CursorConstants
import org.apache.commons.lang3.time.DurationFormatUtils

class Song {

    lateinit var id: String
    lateinit var title: String
    lateinit var artist: String
    lateinit var artist_id: String
    lateinit var album: String
    lateinit var album_id: String
    lateinit var date_added: String
    lateinit var duration: String
    lateinit var path: String
    lateinit var size: String


    companion object {

        fun formatCursorToSong(cursor: Cursor, duration: Long): Song {
            val song = Song()
            song.id = cursor.getString(CursorConstants.SONG_ID_COLUMN)
            song.album_id = cursor.getString(CursorConstants.ALBUM_ID_COLUMN)
            song.artist_id = cursor.getString(CursorConstants.ARTIST_ID_COLUMN)
            song.album = cursor.getString(CursorConstants.ALBUM_COLUMN)
            song.artist = cursor.getString(CursorConstants.ARTIST_COLUMN)
            song.title = cursor.getString(CursorConstants.TITLE_COLUMN)
            song.date_added = cursor.getString(CursorConstants.DATE_ADDED_COLUMN)
            song.duration = DurationFormatUtils.formatDuration(duration, "mm:ss")
            song.path = cursor.getString(CursorConstants.PATH_COLUMN)
            song.size = cursor.getString(CursorConstants.SIZE_COLUMN)
            return song
        }
    }
}