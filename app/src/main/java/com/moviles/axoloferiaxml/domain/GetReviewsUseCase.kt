package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.ReviewRepository
import com.moviles.axoloferiaxml.data.model.Review

class GetReviewsUseCase {
    private val repository = ReviewRepository()

    suspend operator fun invoke(keystoreHelper: KeystoreHelper, stallId: Int): Review? = repository.getReview(keystoreHelper, stallId)
}