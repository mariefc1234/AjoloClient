package com.moviles.axoloferiaxml.data

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.GenericResponseProvider
import com.moviles.axoloferiaxml.data.model.PublishReview
import com.moviles.axoloferiaxml.data.model.Review
import com.moviles.axoloferiaxml.data.model.ReviewProvider
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallCreate
import com.moviles.axoloferiaxml.data.model.StallProvider
import com.moviles.axoloferiaxml.data.network.ReviewService

class ReviewRepository {
    private val api = ReviewService()

    suspend fun getReview(keystoreHelper: KeystoreHelper, stallId: Int): Review? {
        val token = keystoreHelper.getToken()
        val response = api.getReview(token ?: "", stallId)
        if (response != null) {
            ReviewProvider.review = response
        }
        return response
    }

    suspend fun sendReview(review: PublishReview, keystoreHelper: KeystoreHelper) : GenericResponse? {
        val token = keystoreHelper.getToken()
        val response = api.sendReview(review, token ?: "")
        if(response != null){
            GenericResponseProvider.response = response
        }
        return response
    }

    suspend fun deleteReview(keystoreHelper: KeystoreHelper, reviewId: Int): GenericResponse? {
        val token = keystoreHelper.getToken()
        val response = api.deleteReview(token ?: "", reviewId)
        if (response != null) {
            GenericResponseProvider.response = response
        }
        return response
    }
}