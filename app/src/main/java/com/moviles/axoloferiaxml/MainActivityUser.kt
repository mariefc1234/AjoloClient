package com.moviles.axoloferiaxml

import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.media.VolumeShaper
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.moviles.axoloferiaxml.databinding.ActivityMainUserBinding
import java.util.Locale


class MainActivityUser : AppCompatActivity() {

    private var userName = ""
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("userName")) {
            userName = intent.getStringExtra("userName").toString()
            supportActionBar?.title = "Hola, $userName"
            val color = ContextCompat.getColor(this, R.color.color_primary)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
        } else {
            supportActionBar?.title = "Error"
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)


        val languageCode = preferences.getString("language", Locale.getDefault().language)

        val locale = Locale(languageCode)
        val config: Configuration? = resources.configuration
        config!!.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentUser) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.navViewUser
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    fun getUserName(): String {
        return userName
    }
    fun ocultarBarraNavegacion() {
        binding.navViewUser.visibility = View.GONE
    }
    fun mostrarBarraNavegacion() {
        binding.navViewUser.visibility = View.VISIBLE
    }
}