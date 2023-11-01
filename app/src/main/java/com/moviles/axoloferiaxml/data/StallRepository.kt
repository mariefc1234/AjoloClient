package com.moviles.axoloferiaxml.data

import android.util.Log
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallProvider
import com.moviles.axoloferiaxml.data.model.UserProvider
import com.moviles.axoloferiaxml.data.network.StallService

class StallRepository {
    private val api = StallService()

    suspend fun getStalls(token:String): Stall? {
        val response = api.getStall(token)
        if (response != null) {
            StallProvider.stall = response
            Log.d("xd", "repository no es nulo")
        }
        return response
    }
}