package com.moviles.axoloferiaxml.ui.reviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Review
import com.moviles.axoloferiaxml.databinding.FragmentReviewsBinding
import com.moviles.axoloferiaxml.ui.reviews.adapters.ReviewAdapter
import com.moviles.axoloferiaxml.ui.reviews.adapters.ReviewAdapterListener

class ReviewsFragment: Fragment(), ReviewAdapterListener {

    private val viewAdapter by lazy {
        ReviewAdapter(reviewList, this)
    }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var reviewViewModel: ReviewViewModel

    private val reviewList = mutableListOf<Review.ReviewData>()

    private var _binding: FragmentReviewsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = viewManager
        binding.recyclerView.adapter = viewAdapter
        reviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        reviewViewModel.reviewResult.observe(viewLifecycleOwner, Observer {
            val reviewResult = it ?: return@Observer

            if (reviewResult.error != null) {
                showReviewFailed(reviewResult.error)
            }
            if (reviewResult.success != null) {
                loadReviewList(reviewResult.success)
            }

        })
        getReviews()
        return root
    }

    private fun loadReviewList(success: MutableList<Review.ReviewData>) {
        reviewList.clear()
        reviewList.addAll(success)
        viewAdapter.notifyDataSetChanged()
    }

    private fun getReviews() {
        val reviewIdString = AddReviewFragmentArgs.fromBundle(requireArguments()).review
        Log.d("stallId_review", reviewIdString)
        reviewViewModel.getReviewList(this.requireContext(), reviewIdString)
    }

    private fun showReviewFailed(error: Int) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteButtonSelectedListener(review: Review.ReviewData) {
        reviewViewModel.deleteReview(this.requireContext(), review.id!!)
        Toast.makeText(context, review.id.toString(), Toast.LENGTH_SHORT).show()
        getReviews()
    }
}