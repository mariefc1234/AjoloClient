package com.moviles.axoloferiaxml.ui.home_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.FragmentStallDetailBinding
import com.squareup.picasso.Picasso

class StallDetailFragment : Fragment() {
    private var _binding: FragmentStallDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStallDetailBinding.inflate(inflater, container, false)

        // Recuperar el objeto del Bundle
        val stallJson = arguments?.getString("stall")

        if (stallJson != null) {
            val gson = Gson()
            val stall = gson.fromJson(stallJson, Stall.StallList.StallData::class.java)
            fillData(stall)
        }
        val root: View = binding.root
        return root
    }

    private fun fillData(stall : Stall.StallList.StallData) {
        with(binding){
            Picasso.get().load(stall?.image_url).into(stallImage)
            stallName.text = stall?.name
            stallPrice.text = "$ ${stall?.cost}"
            stallStars.text = stall?.id_stall_type
            stallDescription.text = stall?.description
        }
    }
}