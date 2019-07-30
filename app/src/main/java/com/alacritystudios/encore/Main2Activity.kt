package com.alacritystudios.encore

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alacritystudios.encore.data.repository.MediaRepository

class Main2Activity : AppCompatActivity() {

    val MY_PERMISSIONS_REQUEST_READ_MEDIA = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        permission()
//        var mediaRepository = MediaRepository(this.contentResolver)

//        mediaRepository.fetchAllSongs().observe(this, Observer<List<Song>> { newName ->
//            // Update the UI, in this case, a TextView.
//            findViewById<TextView>(R.id.text).setText(newName.size)
//        })
    }

    fun permission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_MEDIA
            )
        } else {
//            readDataExternal()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_MEDIA -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                readDataExternal()
            }

            else -> {
            }
        }
    }
}
