package com.moviles.axoloferiaxml.data.network.user_employee

import com.moviles.axoloferiaxml.data.model.user_employee.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface EmployeeAPIClient {
    @POST("auth/registerClient")
    fun register(@Body request: RegisterRequest): Call<ResponseBody>
}
