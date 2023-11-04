package com.moviles.axoloferiaxml

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moviles.axoloferiaxml.databinding.ActivityMainBinding
import com.moviles.axoloferiaxml.databinding.ActivityUserBinding

class MainUser : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_user)
        if (intent.hasExtra("userName")) {
            val userName = intent.getStringExtra("userName")
            supportActionBar?.title = "Hola, $userName"
        } else {
            supportActionBar?.title = "Error"
        }


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home_user, R.id.navigation_calendar_user, R.id.navigation_qr_user, R.id.navigation_fav_user, R.id.navigation_more_user
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}
