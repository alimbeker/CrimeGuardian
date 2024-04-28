package com.example.crimeguardian.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.crimeguardian.R
import com.example.crimeguardian.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomMenu)

        bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.incidentsFragment -> navController.navigate(R.id.issuesFragment)
                R.id.newsFragment -> navController.navigate(R.id.newsFragment)
                R.id.profileFragment -> navController.navigate(R.id.profileFragment)
            }
            true
        }
    }
}