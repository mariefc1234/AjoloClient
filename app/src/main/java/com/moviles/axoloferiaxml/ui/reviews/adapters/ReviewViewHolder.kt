package com.moviles.axoloferiaxml.ui.reviews.adapters

import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.Review
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.ItemReviewBinding
import com.moviles.axoloferiaxml.databinding.ItemStallBinding
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallAdapterListener
import com.squareup.picasso.Picasso

class ReviewViewHolder (private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(review: Review.ReviewData, listener: ReviewAdapterListener){
        with(binding){

            description.text = review.description

            when(review.points) {
                0.5 -> fillStars(binding, 0..1)
                1.0 -> fillStars(binding, 0..2)
                1.5 -> fillStars(binding, 0..3)
                2.0 -> fillStars(binding, 0..4)
                2.5 -> fillStars(binding, 0..5)
                3.0 -> fillStars(binding, 0..6)
                3.5 -> fillStars(binding, 0..7)
                4.0 -> fillStars(binding, 0..8)
                4.5 -> fillStars(binding, 0..9)
                5.0 -> fillStars(binding, 0..10)

            }

            reviewDelete.setOnClickListener {
                listener.onDeleteButtonSelectedListener(review)
            }

        }
    }

    private fun fillStars(binding: ItemReviewBinding, points: IntRange) {
        val starFill = R.drawable.star_fill
        val starMiddle = R.drawable.midle_star
        var pointsAux = points
        with(binding) {
            stars.forEach {
                for (i in pointsAux) {
                    when(i) {
                        1,3,5,7,9 -> it.setBackgroundResource(starMiddle)
                        else -> it.setBackgroundResource(starFill)
                    }
                }
                pointsAux = pointsAux.first..pointsAux.last -2
            }
        }
    }
}