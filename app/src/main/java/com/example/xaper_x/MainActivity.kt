package com.example.xaper_x

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.ImageViewCompat
import com.example.xaper_x.databinding.ActivityMainBinding
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private val multiplePermissionsId = 14
    private val multiplePermissionsList = if (Build.VERSION.SDK_INT >= 33) {
        arrayListOf(
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        arrayListOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var actionBar: ActionBar

    companion object {
        var videoList: ArrayList<Video> = ArrayList()
        var folderList:ArrayList<Folder> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.coolPinkNav)
        setContentView(binding.root)

        window.navigationBarColor = getColor(android.R.color.white)
        actionBar = supportActionBar!!

        toggle = ActionBarDrawerToggle(this, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (checkMultiplePermissions()) {
            folderList=ArrayList()
            videoList = getAllVideos()
            setFragment(VideosFragment())
        }


        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.videoview -> setFragment(VideosFragment())
                R.id.folderview -> setFragment(folderFragment())
                R.id.profile -> setFragment(HomeFragment())
            }
            return@setOnItemSelectedListener true
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.feedbackNav -> Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show()
                R.id.themesNav -> Toast.makeText(this, "Themes", Toast.LENGTH_SHORT).show()
                R.id.sortOrderNav -> Toast.makeText(this, "Sort Order", Toast.LENGTH_SHORT).show()
                R.id.aboutNav -> Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                R.id.exitNav -> exitProcess(1)
            }
            return@setNavigationItemSelectedListener true
        }




    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentFL, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun checkMultiplePermissions(): Boolean {
        val permissionsToRequest = arrayListOf<String>()
        for (permission in multiplePermissionsList) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                multiplePermissionsId
            )
            return false
        }
        return true
    }

    private fun getAllVideos(): ArrayList<Video> {
        val tempList = ArrayList<Video>()
        val tempFolderList= ArrayList<String>()
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
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null,
            MediaStore.Video.Media.DATE_ADDED + " DESC"
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val titleC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                val idC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val folderC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                val folderIDc=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID))
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
                    if(!tempFolderList.contains(folderC)){
                        tempFolderList.add(folderC)
                        folderList.add(Folder(id=folderIDc, folderName = folderC))
                    }
                } catch (e: Exception) {
                    // Handle exception
                }
            }
            cursor.close()
        }
        return tempList
    }
}
