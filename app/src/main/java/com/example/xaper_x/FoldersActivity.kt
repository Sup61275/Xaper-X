package com.example.xaper_x

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xaper_x.databinding.ActivityFoldersBinding
import java.io.File

class FoldersActivity : AppCompatActivity() {
    companion object {
        lateinit var currentFolderVideos: ArrayList<Video>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFoldersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val folderName = intent.getStringExtra("folderName")
        val folderId = intent.getStringExtra("folderId") // Get folder ID

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = folderName

        currentFolderVideos = folderId?.let { getAllVideos(it) }!! // Fetch videos using folder ID

        binding.videoRVFA.setHasFixedSize(true)
        binding.videoRVFA.setItemViewCacheSize(10)
        binding.videoRVFA.layoutManager = LinearLayoutManager(this@FoldersActivity)
        binding.videoRVFA.adapter = VideoAdapter(this@FoldersActivity, currentFolderVideos)

        binding.totalVideosFA.text = "Total Videos: ${currentFolderVideos.size}"
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun getAllVideos(folderId: String): ArrayList<Video> {
        val tempList = ArrayList<Video>()
        val selection = "${MediaStore.Video.Media.BUCKET_ID} = ?"
        val selectionArgs = arrayOf(folderId)
        val projection = arrayOf(
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.BUCKET_ID
        )
        val cursor = this.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs,
            MediaStore.Video.Media.DATE_ADDED + " DESC"
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val titleC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                val idC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val folderC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))

                val sizeC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                val pathC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                val durationC = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))

                try {
                    val file = File(pathC)
                    val artUriC = Uri.fromFile(file)
                    val video = Video(
                        title = titleC,
                        id = idC,
                        folderName = folderC,
                        duration = durationC,
                        path = pathC,
                        artUri = artUriC,
                        Size = sizeC
                    )
                    if (file.exists()) tempList.add(video)
                } catch (e: Exception) {
                    // Handle exception
                }
            }
            cursor.close()
        }
        return tempList
    }
}
