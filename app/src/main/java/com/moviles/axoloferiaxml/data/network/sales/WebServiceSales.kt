package com.moviles.axoloferiaxml.data.network.sales

import com.google.gson.GsonBuilder
import com.moviles.axoloferiaxml.data.model.offers.OfferGet
import com.moviles.axoloferiaxml.data.model.sales.GetSalesResponse
import com.moviles.axoloferiaxml.data.model.sales.SalePost
import com.moviles.axoloferiaxml.data.model.sales.SalePostResponse
import com.moviles.axoloferiaxml.data.model.sales.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

object AppConstantes {
    const val BASE_URL = "https://ajoloback-production.up.railway.app/api/"
}

interface WebServiceSales {
    @GET("qr/validate/{code_qr}")
    suspend fun getUser(
        @Header("authtoken") token: String?,
        @Path("code_qr") code: String
    ): Response<User>


    @GET("coupon/validate/{code}/{uuid}")
    suspend fun getCoupon(
        @Header("authtoken") token: String?,
        @Path("code") code: String,
        @Path("uuid") uuid: String
    ): Response<OfferGet>

    @POST("shoppings/cash")
    suspend fun shopCoins(
        @Header("authtoken") token: String?,
        @Body sale: SalePost
    ): Response<SalePostResponse>

    @GET("shoppings/getShoppings/{uuid}")
    suspend fun getShoppings(
        @Header("authtoken") token: String?,
        @Path("uuid") uuid: String
    ): Response<GetSalesResponse>


}

object RetrofitClientSales {
    val webServiceSales: WebServiceSales by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebServiceSales::class.java)
    }
}