package com.moviles.axoloferiaxml.data

import com.moviles.axoloferiaxml.data.model.Qr
import com.moviles.axoloferiaxml.data.model.QrProvider
import com.moviles.axoloferiaxml.data.network.QrService


class QrRepository {
    private val api = QrService()

    suspend fun generateQr(string: String): Qr? {
        val response = api.generateQr(string = string)
        if (response != null) {
            QrProvider.qr = response
        }
        return response
    }
}