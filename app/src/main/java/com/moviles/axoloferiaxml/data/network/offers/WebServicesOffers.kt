package com.moviles.axoloferiaxml.data.network.offers

import com.google.gson.GsonBuilder
import com.moviles.axoloferiaxml.data.model.offers.OfferGet
import com.moviles.axoloferiaxml.data.model.offers.OffersDeleteResponse
import com.moviles.axoloferiaxml.data.model.offers.OffersGetAllsResponse
import com.moviles.axoloferiaxml.data.model.offers.OffersPost
import com.moviles.axoloferiaxml.data.model.sales.GetSalesResponse
import com.moviles.axoloferiaxml.data.model.sales.SalePost
import com.moviles.axoloferiaxml.data.model.sales.SalePostResponse
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeDelete
import com.moviles.axoloferiaxml.data.network.sales.AppConstantes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

object AppConstantes {
    const val BASE_URL = "https://ajoloback-production.up.railway.app/api/"
}


interface WebServicesOffers {
    @POST("coupon/create")
    suspend fun addCoupon(
        @Header("authtoken") token: String?,
        @Body coupon : OffersPost
    ): Response<OffersPost>

    @GET("coupon/coupons")
    suspend fun getCoupons(
        @Header("authtoken") token: String?
    ): Response<OffersGetAllsResponse>

    @DELETE("coupon/delete/{id}")
    suspend fun deleteCoupons(
        @Header("authtoken") token: String?,
        @Path("id") id: Int
    ): Response<OffersDeleteResponse>

}

object RetrofitClientOffers {
    val webServiceOffers: WebServicesOffers by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebServicesOffers::class.java)
    }
}