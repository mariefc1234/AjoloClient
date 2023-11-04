package com.moviles.axoloferiaxml.ui.userqr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moviles.axoloferiaxml.databinding.CalendarUserBinding
import com.moviles.axoloferiaxml.databinding.QrUserBinding
import com.moviles.axoloferiaxml.ui.usercalendar.CalendarUserViewModel

class QrUserFrament: Fragment() {

    private var _binding: QrUserBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val qrUserViewModel =
            ViewModelProvider(this).get(QrUserViewModel::class.java)

        _binding = QrUserBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}