package com.moviles.axoloferiaxml.data.network

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
//import androidx.preference.PreferenceManager
import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.Stall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
}