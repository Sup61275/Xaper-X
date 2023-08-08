package com.example.xaper_x

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import com.example.xaper_x.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var actionBar: ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.coolPinkNav)
        setContentView(binding.root)

        window.navigationBarColor = getColor(android.R.color.white)
        actionBar = supportActionBar!!
        //For Nav Drawer
        toggle= ActionBarDrawerToggle(this,binding.root,R.string.open,R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()//we need to sync its state continuously, i.e we should know if something is clicked.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setFragment(VideosFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.videoview -> setFragment(VideosFragment())
                R.id.folderview -> setFragment(folderFragment())
                R.id.profile -> setFragment(HomeFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentFL, fragment)
        transaction.addToBackStack(null)  // Allow back navigation through fragment history
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

}