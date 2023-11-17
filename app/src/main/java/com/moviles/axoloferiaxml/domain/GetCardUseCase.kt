package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.CardRepository
import com.moviles.axoloferiaxml.data.model.Card

class GetCardUseCase {
    private val repository = CardRepository()
    suspend operator fun invoke(keystoreHelper: KeystoreHelper): Card? = repository.getCard(keystoreHelper)
}