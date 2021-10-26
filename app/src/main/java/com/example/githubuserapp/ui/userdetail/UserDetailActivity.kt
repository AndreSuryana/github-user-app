package com.example.githubuserapp.ui.userdetail

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bottom Navigation Setup
        val navView: BottomNavigationView = binding.navView

        // Navigation Controller Setup
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_bottom_nav_detail) as NavHostFragment
        val navController = navHostFragment.navController
        // val navController = findNavController(R.id.nav_host_fragment_activity_bottom_nav_detail)
        navView.setupWithNavController(navController)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
    }
}