package com.moviles.axoloferiaxml.data

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.GenericResponseProvider
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallCreate
import com.moviles.axoloferiaxml.data.model.StallProvider
import com.moviles.axoloferiaxml.data.model.StallUpdate
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

    suspend fun createStall(stall: StallCreate, keystoreHelper: KeystoreHelper) : GenericResponse? {
        val token = keystoreHelper.getToken()
        val response = api.createStall(stall, token ?: "")
        if(response != null){
            GenericResponseProvider.response = response
        }
        return response
    }

    suspend fun updateStall(stall: StallUpdate, keystoreHelper: KeystoreHelper) : GenericResponse? {
        val token = keystoreHelper.getToken()
        val response = api.updateStall(stall, token ?: "")
        if(response != null){
            GenericResponseProvider.response = response
        }
        return response
    }

    suspend fun deleteStalls(keystoreHelper: KeystoreHelper, id: Int): GenericResponse? {
        val token = keystoreHelper.getToken()
        val response = api.deleteStall(token ?: "", id)
        if (response != null) {
            GenericResponseProvider.response = response
        }
        return response
    }
}