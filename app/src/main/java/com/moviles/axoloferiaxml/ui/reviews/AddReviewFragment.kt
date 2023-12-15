package com.moviles.axoloferiaxml.ui.reviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.PublishReview
import com.moviles.axoloferiaxml.data.model.Review
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.FragmentAddReviewBinding
import com.moviles.axoloferiaxml.databinding.FragmentHomeUserBinding
import com.moviles.axoloferiaxml.ui.home_user.HomeUserFragmentDirections
import com.moviles.axoloferiaxml.ui.home_user.HomeViewModel
import com.moviles.axoloferiaxml.ui.home_user.StallDetailFragmentArgs
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallAdapter

class AddReviewFragment: Fragment() {

    private lateinit var reviewViewModel: ReviewViewModel

    private var _binding: FragmentAddReviewBinding? = null

    private val binding get() = _binding!!

    private var stars: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        reviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        reviewViewModel.reviewResult.observe(viewLifecycleOwner, Observer {
            val reviewResult = it ?: return@Observer

            if (reviewResult.error != null) {
                showReviewFailed(reviewResult.error)
            }
            if (reviewResult.success != null) {
                sendReviewSuccess(reviewResult.success)
            }

        })
        with(binding) {
            starsIcon.setOnClickListener { fillStars(0) }
            starsIcon2.setOnClickListener { fillStars(1) }
            starsIcon3.setOnClickListener { fillStars(2) }
            starsIcon4.setOnClickListener { fillStars(3) }
            starsIcon5.setOnClickListener { fillStars(4) }
            buttonPublishReview.setOnClickListener {
                buttonPublishReview.isEnabled = false
                publisReview()
            }
        }
        return root
    }

    private fun publisReview() {
        val publishReview: PublishReview = getData()
        reviewViewModel.sendReview(publishReview, this.requireContext())
        val navController = NavHostFragment.findNavController(this)
        val action = AddReviewFragmentDirections.actionAddReviewUserFragmentToHomeUserFragment()
        navController.navigate(action)
    }

    private fun fillStars(points: Int) {
        binding.stars.forEach { it.setBackgroundResource(R.drawable.star) }
        stars = 0.0
        for(i in 0..points){
            binding.stars.get(i).setBackgroundResource(R.drawable.star_fill)
            stars += 1.0
        }
        stars -= 1.0
    }

    private fun getData(): PublishReview {
        val description = binding.reviewText.text.toString()
        val reviewStallId = AddReviewFragmentArgs.fromBundle(requireArguments()).review
        val points = stars
        Log.d("points", points.toString())
        val stall = PublishReview(
            idStall = reviewStallId.toInt(),
            points = points,
            description = description
        )
        return stall
    }

    private fun sendReviewSuccess(success: Any) {

    }

    private fun showReviewFailed(error: Any) {
        TODO("Not yet implemented")
    }
}