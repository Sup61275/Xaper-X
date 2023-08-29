package com.example.xaper_x

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xaper_x.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class PlayerActivity : AppCompatActivity() {
    lateinit var binding:ActivityPlayerBinding
    companion object{
       lateinit var player:SimpleExoPlayer
       lateinit var playerList:ArrayList<Video>
       var position: Int=-1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeLayout()

    }

    private fun initializeLayout() {
        when(intent.getStringExtra("class")){
            "AllVideos"->{
                playerList= ArrayList()
                playerList.addAll(MainActivity.videoList)
            }
            "FolderActivity"-> {
                playerList = ArrayList()
                playerList.addAll(FoldersActivity.currentFolderVideos)
            }
        }
        createPlayer()
    }

    private fun createPlayer(){
        binding.VideoTitle.text= playerList[position].title
        binding.VideoTitle.isSelected=true
        player = SimpleExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri(playerList[position].artUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    }