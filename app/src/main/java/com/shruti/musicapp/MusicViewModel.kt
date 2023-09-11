package com.shruti.musicapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel() {
    var musicContentList : MutableLiveData<ArrayList<MusicContent>> = MutableLiveData(arrayListOf<MusicContent>())
}