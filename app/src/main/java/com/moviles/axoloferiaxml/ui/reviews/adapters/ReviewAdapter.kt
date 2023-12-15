package com.moviles.axoloferiaxml.ui.reviews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Review
import com.moviles.axoloferiaxml.databinding.ItemReviewBinding

interface ReviewAdapterListener{
    fun onDeleteButtonSelectedListener(review: Review.ReviewData)
}

class ReviewAdapter (private val items: MutableList<Review.ReviewData>, private val listener: ReviewAdapterListener): RecyclerView.Adapter<ReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(view, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val stall = items[position]
        holder.bindData(stall, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}