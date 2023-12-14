package com.moviles.axoloferiaxml.ui.more_admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.ui.login.LoginActivity

class MoreAdminFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_more_admin, container, false)

        //Funcion Logout
        val logoutButton = root.findViewById<CardView>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
        }

        //Asigna Username
        val textViewUsername = root.findViewById<TextView>(R.id.textViewUserName)
        textViewUsername.text = (requireActivity() as MainActivity).getUserName()

        //Funcion Support
        val supportBtn = root.findViewById<LinearLayout>(R.id.btnSupportAdmin)
        supportBtn.setOnClickListener {
            findNavController().navigate(R.id.action_moreAdminFragment_to_supportUserFragment2)
        }
        //Funcion Languaje
        val languajeBtn = root.findViewById<LinearLayout>(R.id.languageAdmin)
        languajeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_moreAdminFragment_to_languageUserFragment)
        }

        return root
    }
}