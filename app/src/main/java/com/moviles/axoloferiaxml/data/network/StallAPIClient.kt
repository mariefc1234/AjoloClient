package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallCreate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface StallAPIClient {
    @GET("stalls")
    suspend fun getStall(@Header("authtoken") token: String): Response<Stall>

    @POST("stalls/create")
    suspend fun createStall(
        @Body stall: StallCreate,
        @Header("authtoken") token: String
    ): Response<GenericResponse>
}