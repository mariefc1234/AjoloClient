package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.ReviewRepository
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.Review

class DeleteReviewUseCase {
    private val repository = ReviewRepository()

    suspend operator fun invoke(keystoreHelper: KeystoreHelper, reviewId: Int): GenericResponse? = repository.deleteReview(keystoreHelper, reviewId)
}