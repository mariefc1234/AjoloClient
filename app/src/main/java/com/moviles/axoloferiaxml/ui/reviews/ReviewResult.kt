package com.moviles.axoloferiaxml.ui.reviews

import com.moviles.axoloferiaxml.data.model.Review


class ReviewResult(
    val success: MutableList<Review.ReviewData>? = null,
    val error: Int? = null
)