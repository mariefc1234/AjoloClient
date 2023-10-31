package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.data.QrRepository
import com.moviles.axoloferiaxml.data.model.Qr


class GetQrUseCase {
    private val repository = QrRepository()

    suspend operator fun invoke(string: String): Qr? = repository.generateQr(string)
}