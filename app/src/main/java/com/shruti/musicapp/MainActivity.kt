package com.shruti.musicapp

import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.shruti.musicapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    var viewModel : MusicViewModel ?= null
    lateinit var navController : NavController
    var permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            getSongs()
        } else {
        }
    }
    var musicList = ArrayList<MusicContent>()
    var mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        navController = findNavController(R.id.navController)
        binding?.bottomnav?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.playist -> {
                    navController?.navigate(R.id.musicList)
                }

                R.id.playingmusic -> {
                    navController?.navigate(R.id.musicPlay)
                }
            }
            return@setOnItemSelectedListener true

        }
    }


    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            getSongs()
        } else {
            permission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    fun getSongs() {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC
        val cursor: Cursor? = contentResolver?.query(uri, null, selection, null, null)
        if (cursor?.moveToFirst() == true) {
            while (cursor.isLast == false) {
                musicList.add(
                    MusicContent(
                        title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                        duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
                        artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                        storageLocation = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    )
                )
                cursor.moveToNext()
            }
        }

        viewModel?.musicContentList?.value = musicList
    }
}
