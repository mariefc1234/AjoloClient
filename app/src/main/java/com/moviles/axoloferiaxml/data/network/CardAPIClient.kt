package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.data.model.Card
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CardAPIClient {
    @GET("payment/card")
    suspend fun getCard(
        @Header("authtoken") token: String
    ): Response<Card>
}