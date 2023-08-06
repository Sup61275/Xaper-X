package com.example.xaper_x

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.xaper_x.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = getColor(android.R.color.white)
        setFragment(VideosFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.videoview -> setFragment(VideosFragment())
                R.id.folderview -> setFragment(folderFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun setFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentFL,fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}