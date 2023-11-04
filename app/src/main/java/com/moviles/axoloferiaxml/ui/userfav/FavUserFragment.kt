package com.moviles.axoloferiaxml.ui.userfav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moviles.axoloferiaxml.databinding.FavUserBinding
import com.moviles.axoloferiaxml.databinding.QrUserBinding
import com.moviles.axoloferiaxml.ui.userqr.QrUserViewModel

class FavUserFragment : Fragment() {

    private var _binding: FavUserBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favUserViewModel =
            ViewModelProvider(this).get(FavUserViewModel::class.java)

        _binding = FavUserBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}