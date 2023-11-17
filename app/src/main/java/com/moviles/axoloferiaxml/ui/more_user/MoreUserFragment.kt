package com.moviles.axoloferiaxml.ui.more_user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.ui.login.LoginActivity

class MoreUserFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_more_user, container, false)

        //Funcion Logout
        val logoutButton = root.findViewById<TextView>(R.id.logoutButtonUser)
        logoutButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
        }

        //Asigna Username
        val textViewUsername = root.findViewById<TextView>(R.id.user_username)
        textViewUsername.text = (requireActivity() as MainActivityUser).getUserName()

        // Funcion para Comprar Ajolocoins
        val shopCoins = root.findViewById<TextView>(R.id.shopCoinsButton)
        shopCoins.setOnClickListener {
            findNavController().navigate(R.id.action_moreUserFragment_to_shopCoinsUserFragment)
        }

        return root
    }

}