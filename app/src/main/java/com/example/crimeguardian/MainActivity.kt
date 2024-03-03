   package com.example.crimeguardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomnavigation.BottomNavigationView

   class MainActivity : AppCompatActivity() {
       private lateinit var navController: NavController


       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

           val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
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