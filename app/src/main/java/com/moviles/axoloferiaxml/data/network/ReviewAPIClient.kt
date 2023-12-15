package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.PublishReview
import com.moviles.axoloferiaxml.data.model.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewAPIClient {
    @GET("reviews/stall/{stallId}")
    suspend fun getReview(
        @Header("authtoken") token: String,
        @Path("stallId") stallId: Int
    ): Response<Review>

    @POST("reviews/create")
    suspend fun sendReview(
        @Body review: PublishReview,
        @Header("authtoken") token: String
    ): Response<GenericResponse>

    @DELETE("reviews/delete/{reviewId}")
    suspend fun deleteReview(
        @Header("authtoken") token: String,
        @Path("reviewId") reviewId: Int
    ): Response<GenericResponse>
}