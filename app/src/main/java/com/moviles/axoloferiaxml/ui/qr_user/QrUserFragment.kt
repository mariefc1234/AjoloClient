package com.moviles.axoloferiaxml.ui.qr_user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.databinding.FragmentQrUserBinding
import kotlinx.coroutines.launch


class QrUserFragment : Fragment() {

    private var _binding: FragmentQrUserBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val qrUserViewModel = ViewModelProvider(this).get(QrUserViewModel::class.java)
        _binding = FragmentQrUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*
        qrUserViewModel.text.observe(viewLifecycleOwner) {
            textView.text = "AJOLOFERIA"
        }*/

        val textviewCoins = binding.userCoins
        textviewCoins.text = (requireActivity() as MainActivityUser).getUserCoins()

        initQR(qrUserViewModel)

        return root
    }

    private fun initQR(qrUserViewModel: QrUserViewModel) {
        lifecycleScope.launch {
            val qrCodeBitmap = qrUserViewModel.generateQRCode(requireContext())
            val myImageView: ImageView = binding.imgQr
            myImageView.setImageBitmap(qrCodeBitmap)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}