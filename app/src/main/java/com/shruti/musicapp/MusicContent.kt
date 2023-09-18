package com.shruti.musicapp

import java.util.concurrent.TimeUnit

data class MusicContent(
    var title: String = "",
    var duration: String = "",
    var artistName: String = "",
    var isPlaying: String = "",
    var storageLocation: String = ""
)
fun formatDuration(type : Long):String{
    var minutes = TimeUnit.MINUTES.convert(type,TimeUnit.MILLISECONDS)
    var seconds = TimeUnit.SECONDS.convert(type,TimeUnit.MILLISECONDS)-minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES)
    return String.format("$minutes:$seconds")
}
