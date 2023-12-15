package com.moviles.axoloferiaxml.ui.home_stall

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.MainActivityStall
import com.moviles.axoloferiaxml.R

class HomeStallFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_stall, container, false)

        val makesaleCoins = root.findViewById<ImageButton>(R.id.shopButtonCharge)

        makesaleCoins.setOnClickListener {
            findNavController().navigate(R.id.action_homeStallFragment_to_codeScannerSale)
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        val tuActividad = activity as MainActivityStall
        tuActividad.mostrarBarraNavegacion()
    }
}