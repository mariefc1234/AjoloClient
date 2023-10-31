package com.moviles.axoloferiaxml.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.moviles.axoloferiaxml.databinding.FragmentDashboardBinding
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //private var token: String = ""

    //fun setToken(token: String) {
    //    this.token = token
    //}
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = arguments

        if (bundle != null && bundle.containsKey("token")) {
            token = bundle.getString("token")
        }

        //Toast.makeText(this.context, "TOKEN $token", Toast.LENGTH_LONG).show()

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
           textView.text = "AJOLOFERIA"
        }

        initQR(dashboardViewModel)

        return root
    }

    private fun initQR(dashboardViewModel: DashboardViewModel) {
            lifecycleScope.launch {
                val qrCodeBitmap = dashboardViewModel.generateQRCode(token!!)
                val myImageView: ImageView = binding.myImageView
                myImageView.setImageBitmap(qrCodeBitmap)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}