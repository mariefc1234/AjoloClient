package com.moviles.axoloferiaxml.ui.home_user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.FragmentStallDetailBinding
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

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
        val stall: Stall.StallList.StallData
        _binding = FragmentStallDetailBinding.inflate(inflater, container, false)

        val stallJson = StallDetailFragmentArgs.fromBundle(requireArguments()).stall
        if (stallJson != null) {
            val gson = Gson()
            stall = gson.fromJson(stallJson, Stall.StallList.StallData::class.java)
            fillData(stall)
            binding.stallAddReview.setOnClickListener {
                binding.stallAddReview.isEnabled = false
                val navController = NavHostFragment.findNavController(this)
                val action = StallDetailFragmentDirections.actionStallDetailUserFragmentToAddReviewUserFragment(stall.id.toString())
                navController.navigate(action)
            }
            binding.stallSeeReview.setOnClickListener {
                binding.stallSeeReview.isEnabled = false
                val navController = NavHostFragment.findNavController(this)
                val action = StallDetailFragmentDirections.actionStallDetailUserFragmentToReviewsUserFragment(stall.id.toString())
                navController.navigate(action)
            }
        } else {
            binding.stallAddReview.isEnabled = false
            binding.stallSeeReview.isEnabled = false
        }


        val root: View = binding.root
        return root
    }

    private fun fillData(stall : Stall.StallList.StallData) {
        with(binding){
            Picasso.get().load(stall?.image_url).into(stallImage)
            stallName.text = stall?.name
            stallPrice.text = "$ ${stall?.cost}"
            val format = DecimalFormat("#.00")

            stallStars.text = format.format(stall?.points).toString()
            stallDescription.text = stall?.description
        }
    }
}