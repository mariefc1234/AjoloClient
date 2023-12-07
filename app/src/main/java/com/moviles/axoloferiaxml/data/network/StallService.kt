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
            Log.d("response", response.raw().toString())
            Log.d("response", response.code().toString())
            Log.d("response", response.isSuccessful.toString())
            Log.d("response", response.errorBody().toString())
            Log.d("response", response.headers().toString())
            Log.d("response", response.message())
            Log.d("response", response.body().toString())
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}