package com.moviles.axoloferiaxml.data.network


import com.moviles.axoloferiaxml.data.model.Qr
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface QrAPIClient {
    @GET("qr/generate")
    suspend fun generateQr(
        @Header("authtoken") token: String
    ): Response<Qr>
}