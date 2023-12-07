package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.QrRepository
import com.moviles.axoloferiaxml.data.model.User
import org.json.JSONObject

class ValidateQrUseCase {
    private val repository = QrRepository()

    suspend operator fun invoke(
        keystoreHelper: KeystoreHelper,
        qrCode: JSONObject
    ): User? = repository.validateQr(keystoreHelper, qrCode)
}