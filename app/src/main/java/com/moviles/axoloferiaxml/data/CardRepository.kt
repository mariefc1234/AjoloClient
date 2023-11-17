package com.moviles.axoloferiaxml.data

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.Card
import com.moviles.axoloferiaxml.data.model.CardProvider
import com.moviles.axoloferiaxml.data.network.CardService

class CardRepository {
    private val api = CardService()

    suspend fun getCard(keystoreHelper: KeystoreHelper): Card? {
        val token = keystoreHelper.getToken()
        val response = api.getCard(token ?: "")
        if (response != null) {
            CardProvider.card = response
        }
        return response
    }
}