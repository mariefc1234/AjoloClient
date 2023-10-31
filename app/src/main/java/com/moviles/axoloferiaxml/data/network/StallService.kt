package com.moviles.axoloferiaxml.data.network

import android.util.Log
import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.Stall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StallService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getStall(token: String): Stall? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(StallAPIClient::class.java).getStall("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJmZDQ1YzYxYi02MmM1LTQ1MzYtYjFjMy1iYWIwMjE3M2Y1N2UiLCJ1c2VyQWdlbnQiOiJQb3N0bWFuUnVudGltZS83LjM0LjAiLCJ1c2VySXAiOiI6OmZmZmY6MTkyLjE2OC4wLjQiLCJpYXQiOjE2OTg3NzM2NTcsImV4cCI6MTcwMzk1NzY1N30.sCXkKyp-C6C_iqONFuwXrJCjQZlSFRyV4KG8lX7exz0")
            Log.d("service", response.raw().request().toString())
            Log.d("service", response.raw().request().headers().toString())
            Log.d("service", response.raw().request().header("authToken").toString())
            Log.d("service", response.code().toString())

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}