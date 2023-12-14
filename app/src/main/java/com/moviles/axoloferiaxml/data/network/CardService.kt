package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getCard(token: String): Card? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(CardAPIClient::class.java).getCard(token)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}