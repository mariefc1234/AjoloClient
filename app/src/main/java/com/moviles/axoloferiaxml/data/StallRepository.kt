package com.moviles.axoloferiaxml.data

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallProvider
import com.moviles.axoloferiaxml.data.model.UserProvider
import com.moviles.axoloferiaxml.data.network.StallService

class StallRepository {
    private val api = StallService()

    suspend fun getStalls(keystoreHelper: KeystoreHelper): Stall? {
        val token = keystoreHelper.getToken()
        val response = api.getStall(token ?: "")
        if (response != null) {
            StallProvider.stall = response
        }
        return response
    }
}