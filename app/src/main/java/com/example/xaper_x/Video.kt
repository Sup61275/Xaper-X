package com.example.xaper_x

import android.net.Uri

data class Video(
    val id: String,
    val title: String,
    val duration: Long = 0,
    val folderName: String,
    val path: String,
    val artUri: Uri,
    val Size: String
)
