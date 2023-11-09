package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.ui.login.LoginActivity

class ShopCoinsUserFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shop_coins_user, container, false)

        val cashButton = root.findViewById<ConstraintLayout>(R.id.layoutCash)
        cashButton.setOnClickListener {
            Toast.makeText(context, "Pago con cash", Toast.LENGTH_LONG).show()

        }

        return  root
    }

}