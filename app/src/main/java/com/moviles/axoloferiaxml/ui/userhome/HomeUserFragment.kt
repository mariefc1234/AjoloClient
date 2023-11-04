package com.moviles.axoloferiaxml.ui.userhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moviles.axoloferiaxml.databinding.HomeUserBinding
import com.moviles.axoloferiaxml.ui.home.HomeViewModel

class HomeUserFragment : Fragment() {

    private var _binding: HomeUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val homeUserViewModel =
            ViewModelProvider(this).get(HomeUserViewModel::class.java)

        _binding = HomeUserBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}