package com.moviles.axoloferiaxml.data

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.Qr
import com.moviles.axoloferiaxml.data.model.QrProvider
import com.moviles.axoloferiaxml.data.network.QrService


class QrRepository {
    private val api = QrService()

    suspend fun generateQr(keystoreHelper: KeystoreHelper): Qr? {
        val token = keystoreHelper.getToken()
        if (token.isNullOrEmpty() || token.isNullOrBlank()){
            Log.d("error", "el token sigue siendo nulo $token")
        }
        val response = api.generateQr(token ?: "")
        if (response != null) {
            QrProvider.qr = response
        }
        return response
    }
}