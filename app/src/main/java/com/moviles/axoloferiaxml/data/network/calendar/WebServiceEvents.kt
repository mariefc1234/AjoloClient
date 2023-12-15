package com.moviles.axoloferiaxml.data.network.calendar

import com.moviles.axoloferiaxml.data.model.calendar.EventsResponse
import com.google.gson.GsonBuilder
import com.moviles.axoloferiaxml.data.model.calendar.EventsDeleteResponse
import com.moviles.axoloferiaxml.data.model.calendar.EventsPost
import com.moviles.axoloferiaxml.data.model.calendar.EventsPostResponse
import com.moviles.axoloferiaxml.data.model.calendar.EventsPut
import com.moviles.axoloferiaxml.data.model.calendar.EventsPutResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

object AppConstantes {
    const val BASE_URL = "https://ajoloback-production.up.railway.app/api/"
}

interface WebServiceEvents {

    @GET("events/find/{date}")
    suspend fun getEvents(
        @Header("authtoken") token: String?,
        @Path("date") code: String
    ): Response<EventsResponse>

    @POST("events/create")
    suspend fun addEvents(
        @Header("authtoken") token: String?,
        @Body event: EventsPost
    ): Response<EventsPostResponse>

    @PUT("events/update/{id}")
    suspend fun updateEvents(
        @Header("authtoken") token: String?,
        @Path("id") idEvent: Int,
        @Body event: EventsPut
    ): Response<EventsPutResponse>

    @DELETE("events/delete/{id}")
    suspend fun deleteEvents(
        @Header("authtoken") token: String?,
        @Path("id") id: Int
    ): Response<EventsDeleteResponse>
}

object RetrofitClientEvents {
    val webServiceEvents: WebServiceEvents by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebServiceEvents::class.java)
    }
}