package com.example.xaper_x

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.xaper_x.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = getColor(android.R.color.white)
        binding.bottomNav.setOnItemSelectedListener {
            Toast.makeText(this@MainActivity,"Item Clicked",Toast.LENGTH_SHORT).show()
            return@setOnItemSelectedListener true
        }

    }
}