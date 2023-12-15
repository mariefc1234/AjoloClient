package com.moviles.axoloferiaxml.data.network

import android.util.Log
import android.widget.Toast
import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.PublishReview
import com.moviles.axoloferiaxml.data.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getReview(token: String, stallId:Int ): Review? {
        return withContext(Dispatchers.IO) {
            Log.d("id", stallId.toString())
            val response = retrofit.create(ReviewAPIClient::class.java).getReview(token, stallId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun sendReview(review: PublishReview, token: String): GenericResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ReviewAPIClient::class.java).sendReview(review, token)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun deleteReview(token: String, reviewId:Int ): GenericResponse? {
        return withContext(Dispatchers.IO) {
            Log.d("reviewId", reviewId.toString())
            val response = retrofit.create(ReviewAPIClient::class.java).deleteReview(token, reviewId)
            Log.d("error", response.raw().request().toString())
            Log.d("error", response.code().toString())
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}