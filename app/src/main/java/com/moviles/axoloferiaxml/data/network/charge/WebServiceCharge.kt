package com.moviles.axoloferiaxml.data.network.charge

import com.google.gson.GsonBuilder
import com.moviles.axoloferiaxml.data.model.charges.ChargePost
import com.moviles.axoloferiaxml.data.model.charges.ChargePostResponse
import com.moviles.axoloferiaxml.data.model.user_employee.Employee
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeAdd
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


object AppConstantes {
    const val BASE_URL = "https://ajoloback-production.up.railway.app/api/"
}

interface WebServiceCharge {

    @POST("paymentsinstall/create")
    suspend fun addCharge(
        @Header("authtoken") token: String?,
        @Body charge: ChargePost
    ): Response<ChargePostResponse>

}

object RetrofitClientCharge {
    val webServiceCharge: WebServiceCharge by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebServiceCharge::class.java)
    }
}