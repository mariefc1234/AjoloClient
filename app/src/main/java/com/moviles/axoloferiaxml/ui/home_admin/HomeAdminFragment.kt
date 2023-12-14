package com.moviles.axoloferiaxml.ui.home_admin

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.R

class HomeAdminFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home_admin, container, false)


        if ((requireActivity() as MainActivity).getUserType() == "3"){

            val textTitleAdmin = root.findViewById<CardView>(R.id.textTitleAdmin)
            val adminEmployees = root.findViewById<CardView>(R.id.adminEmployees)
            val adminEmployeesAtractions = root.findViewById<CardView>(R.id.adminEmployeesAtractions)

            textTitleAdmin.isVisible = false
            adminEmployees.isVisible = false
            adminEmployeesAtractions.isVisible = false
        }

        //Admin Panel
        val employeeAdminRegisterButton = root.findViewById<ImageButton>(R.id.employeeAdminRegisterButton)
        val employeeAtractionsAdmin = root.findViewById<ImageButton>(R.id.employeeAtractionsAdmin)

        employeeAdminRegisterButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminFragment_to_employeeAdmFragment)
        }
        employeeAtractionsAdmin.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminFragment_to_atranstionsAdminFragment)
        }

        //Management
        val atractionEmployeeButton = root.findViewById<ImageButton>(R.id.atracctionEmployeeButton)
        val sellerEmployeeButton = root.findViewById<ImageButton>(R.id.sellerEmployeeButton)
        val offersEmployeeButton = root.findViewById<ImageButton>(R.id.offersEmployeeButton)
        val eventsEmployeeButton = root.findViewById<ImageButton>(R.id.eventsEmployeeButton)

        atractionEmployeeButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminFragment_to_atractionsEmployeesFragment)
        }
        sellerEmployeeButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminFragment_to_sellersEmployeesFragment)
        }
        offersEmployeeButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminFragment_to_offersEmployeesFragment)
        }
        eventsEmployeeButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminFragment_to_eventsEmployeeFragment)
        }

        //Shopping
        val shopButton = root.findViewById<ImageButton>(R.id.shopButton)

        shopButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminFragment_to_salesCoinsFragment)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val tuActividad = activity as MainActivity
        tuActividad.mostrarBarraNavegacion()
    }

}