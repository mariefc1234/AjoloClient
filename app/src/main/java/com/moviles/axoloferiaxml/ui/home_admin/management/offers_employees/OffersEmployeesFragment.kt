package com.moviles.axoloferiaxml.ui.home_admin.management.offers_employees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.R

class OffersEmployeesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_offers_employees, container, false)
        ocultarBarraNavegacion()
        return root
    }

    private fun ocultarBarraNavegacion() {
        if (activity is MainActivity) {
            val tuActividad = activity as MainActivity
            tuActividad.ocultarBarraNavegacion()
        }
    }

}