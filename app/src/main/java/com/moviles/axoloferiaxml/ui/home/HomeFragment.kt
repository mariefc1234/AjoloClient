package com.moviles.axoloferiaxml.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moviles.axoloferiaxml.databinding.FragmentHomeBinding
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.ui.home.HomeViewModel
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.databinding.FragmentMoreBinding
import com.moviles.axoloferiaxml.ui.more.MoreViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}