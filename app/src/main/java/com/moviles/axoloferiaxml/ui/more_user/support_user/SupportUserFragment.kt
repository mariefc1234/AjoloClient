package com.moviles.axoloferiaxml.ui.more_user.support_user

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R

class SupportUserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_support_user, container, false)
        ocultarBarraNavegacion()

        val btnInstagram: ImageButton = root.findViewById(R.id.btnInstagram)
        val btnTikTok: ImageButton = root.findViewById(R.id.btnTikTok)

        btnInstagram.setOnClickListener {
            openProfile("instagram", "dino.baits")
        }

        btnTikTok.setOnClickListener {
            openProfile("tiktok", "dino.bites")
        }

        return root
    }

    private fun ocultarBarraNavegacion() {
        if (activity is MainActivityUser) {
            val tuActividad = activity as MainActivityUser
            tuActividad.ocultarBarraNavegacion()
        }
    }

    private fun openProfile(platform: String, username: String) {
        val packageName = when (platform) {
            "instagram" -> "com.instagram.android"
            "tiktok" -> "com.zhiliaoapp.musically"
            else -> null
        }

        if (packageName != null && isAppInstalled(requireContext().packageManager, packageName)) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getProfileUrl(platform, username)))
                intent.setPackage(packageName)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error al abrir el perfil en $platform", Toast.LENGTH_SHORT).show()
            }
        } else {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getProfileUrl(platform, username)))
            startActivity(webIntent)
        }
    }

    private fun getProfileUrl(platform: String, username: String): String {
        return when (platform) {
            "instagram" -> "http://instagram.com/_u/$username"
            "tiktok" -> "https://www.tiktok.com/@$username"
            else -> ""
        }
    }

    private fun isAppInstalled(pm: PackageManager, packageName: String): Boolean {
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
