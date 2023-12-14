package com.moviles.axoloferiaxml.ui.more_user.language_user

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import java.util.Locale

class LanguageUserFragment : Fragment() {

    private lateinit var spinnerLanguages: Spinner
    private lateinit var textViewLanguage: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_language_user, container, false)
        ocultarBarraNavegacion()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val resetToDefaultButton: Button = root.findViewById(R.id.resetToDefaultButton)
        resetToDefaultButton.setOnClickListener {
            setLanguageToDefault()
        }

        val changeSpanish: Button = root.findViewById(R.id.changeSpanish)
        changeSpanish.setOnClickListener {
            setLanguage("es")
        }

        return root
    }

    private fun setLanguage(languageCode: String) {
        sharedPreferences.edit().putString("language", languageCode).apply()
        updateResourcesConfiguration(languageCode)

        requireActivity().recreate()
    }

    private fun setLanguageToDefault() {
        val defaultLanguage = Locale.getDefault().language
        sharedPreferences.edit().putString("language", defaultLanguage).apply()
        updateResourcesConfiguration(defaultLanguage)
        requireActivity().recreate()
    }

    private fun updateResourcesConfiguration(languageCode: String) {
        val locale = Locale(languageCode)
        val config = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun ocultarBarraNavegacion() {
        if (activity is MainActivityUser) {
            val tuActividad = activity as MainActivityUser
            tuActividad.ocultarBarraNavegacion()
        }
    }
}
