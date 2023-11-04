package com.moviles.axoloferiaxml.ui.usermore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moviles.axoloferiaxml.databinding.FavUserBinding
import com.moviles.axoloferiaxml.databinding.MoreUserBinding
import com.moviles.axoloferiaxml.ui.userfav.FavUserViewModel

class MoreUserFragment : Fragment() {

    private var _binding: MoreUserBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val moreUserViewModel =
            ViewModelProvider(this).get(MoreUserViewModel::class.java)

        _binding = MoreUserBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}