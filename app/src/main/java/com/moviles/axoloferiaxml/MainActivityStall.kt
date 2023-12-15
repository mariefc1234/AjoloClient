package com.moviles.axoloferiaxml

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.moviles.axoloferiaxml.databinding.ActivityMainStallBinding

class MainActivityStall: AppCompatActivity() {

    private var userName = ""
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainStallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainStallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("userName")) {
            userName = intent.getStringExtra("userName").toString()
            supportActionBar?.title = "Hola, $userName"
            val color = ContextCompat.getColor(this, R.color.color_primary)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
        } else {
            supportActionBar?.title = "Error"
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentStall) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.navViewStall
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
    fun getUserName(): String {
        return userName
    }

    fun ocultarBarraNavegacion() {
        binding.navViewStall.visibility = View.GONE
    }
    fun mostrarBarraNavegacion() {
        binding.navViewStall.visibility = View.VISIBLE
    }
}