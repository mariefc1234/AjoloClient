package com.moviles.axoloferiaxml.data.network.offers

import com.google.gson.GsonBuilder
import com.moviles.axoloferiaxml.data.model.offers.OfferGet
import com.moviles.axoloferiaxml.data.network.sales.AppConstantes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

object AppConstantes {
    const val BASE_URL = "https://ajoloback-production.up.railway.app/api/"
}


interface WebServicesOffers {

}

object RetrofitClientSales {
    val webServiceOffers: WebServicesOffers by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebServicesOffers::class.java)
    }
}