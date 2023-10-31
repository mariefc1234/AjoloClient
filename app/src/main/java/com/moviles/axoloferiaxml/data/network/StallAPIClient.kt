package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.data.model.Stall
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface StallAPIClient {
    @GET("stalls")
    suspend fun getStall(@Header("authToken") token: String): Response<Stall>
}