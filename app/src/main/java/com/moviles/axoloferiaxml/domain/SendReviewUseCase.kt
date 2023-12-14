package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.ReviewRepository
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.PublishReview
import com.moviles.axoloferiaxml.data.model.Review

class SendReviewUseCase {
    private val repository = ReviewRepository()

    suspend operator fun invoke(review: PublishReview, keystoreHelper: KeystoreHelper): GenericResponse? = repository.sendReview(review, keystoreHelper)
}