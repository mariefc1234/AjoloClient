package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.QrRepository
import com.moviles.axoloferiaxml.data.model.Qr


class GetQrUseCase {
    private val repository = QrRepository()

    suspend operator fun invoke(keystoreHelper: KeystoreHelper): Qr? = repository.generateQr(keystoreHelper)
}