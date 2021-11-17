package com.example.phototester.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.phototester.R
import com.example.phototester.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initMainView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initMainView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.camera -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CameraFragment.newInstance())
                        .commit()
                    true
                }
                R.id.gallery -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, GalleryFragment.newInstance())
                        .commit()
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.camera
    }
}