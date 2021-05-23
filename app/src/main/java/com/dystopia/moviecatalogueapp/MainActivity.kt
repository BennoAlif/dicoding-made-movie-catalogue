package com.dystopia.moviecatalogueapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dystopia.moviecatalogueapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupBottomNav()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupBottomNav() {
        val bottomNavView = binding?.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        if (bottomNavView != null) {
            NavigationUI.setupWithNavController(
                bottomNavView,
                navHostFragment.navController
            )
        }
    }
}