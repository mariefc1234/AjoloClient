package com.moviles.axoloferiaxml.data.network


import com.moviles.axoloferiaxml.data.model.Qr
import com.moviles.axoloferiaxml.data.model.User
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface QrAPIClient {
    @GET("qr/generate")
    suspend fun generateQr(
        @Header("authtoken") token: String
    ): Response<Qr>

    @POST("qr/validate")
    suspend fun validateQR(
        @Header("authtoken") token: String,
        @Body params: JSONObject
    ): Response<User>
}