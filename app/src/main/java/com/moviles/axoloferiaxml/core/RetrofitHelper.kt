package com.moviles.axoloferiaxml.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://ajoloback-production.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}