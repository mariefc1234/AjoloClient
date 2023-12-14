package com.moviles.axoloferiaxml.data.network

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
//import androidx.preference.PreferenceManager
import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallCreate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class StallService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getStall(token: String): Stall? {

        return withContext(Dispatchers.IO) {
            val response = retrofit.create(StallAPIClient::class.java).getStall(token)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun createStall(stall: StallCreate, token: String): GenericResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(StallAPIClient::class.java).createStall(stall, token)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun updateStall(stall: StallCreate, token: String): GenericResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(StallAPIClient::class.java).updateStall(stall, stall.id!!, token)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun deleteStall(token: String, id: Int): GenericResponse? {

        return withContext(Dispatchers.IO) {
            val response = retrofit.create(StallAPIClient::class.java).deleteStall(token, id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}