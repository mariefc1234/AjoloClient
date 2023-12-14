package com.moviles.axoloferiaxml

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moviles.axoloferiaxml.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var userName = ""
    private  lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.hasExtra("userName")) {
            userName = intent.getStringExtra("userName").toString()
            supportActionBar?.title = getString(R.string.welcomeMessage) + userName
            val color = ContextCompat.getColor(this, R.color.purple_1)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
        } else {
            supportActionBar?.title = "Error"
        }

        type = intent.getStringExtra("role").toString()



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.navView
        setupWithNavController(bottomNavigationView, navController)
    }

    fun getUserType(): String {
        return type
    }

    fun getUserName(): String {
        return userName
    }
    fun ocultarBarraNavegacion() {
        binding.navView.visibility = View.GONE
    }
    fun mostrarBarraNavegacion() {
        binding.navView.visibility = View.VISIBLE
    }

}