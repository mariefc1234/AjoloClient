package com.moviles.axoloferiaxml

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moviles.axoloferiaxml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

//        val tokenUser = intent.getStringExtra("token")
//        Toast.makeText(this, "GETSTREx $tokenUser", Toast.LENGTH_LONG).show()
//
        val bundle = Bundle()
//        bundle.putString("token", tokenUser)


        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        navController.setGraph(R.navigation.mobile_navigation, bundle)
        navController.setGraph(navController.graph, bundle)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        navController.navigate(R.id.navigation_home, bundle)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}