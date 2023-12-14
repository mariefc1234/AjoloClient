package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.Review
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallCreate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StallAPIClient {
    @GET("stalls")
    suspend fun getStall(@Header("authtoken") token: String): Response<Stall>

    @POST("stalls/create")
    suspend fun createStall(
        @Body stall: StallCreate,
        @Header("authtoken") token: String
    ): Response<GenericResponse>

    @PUT("stalls/update")
    suspend fun updateStall(
        @Body stall: StallCreate,
        @Header("authtoken") token: String
    ): Response<GenericResponse>

    @DELETE("stalls/{stallId}")
    suspend fun deleteStall(
        @Header("authtoken") token: String,
        @Path("stallId") stallId: Int
    ): Response<GenericResponse>
}