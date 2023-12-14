package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.Qr
import com.moviles.axoloferiaxml.data.model.QrData
import com.moviles.axoloferiaxml.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class QrService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun generateQr(token: String): Qr? {
        return withContext(Dispatchers.IO) {

            val response = retrofit.create(QrAPIClient::class.java).generateQr(token)
            if (response.isSuccessful) {
                response.body() ?: Qr("error", "Error en la solicitud", QrData("ERROR CON USUARIO"))
            } else {
                Qr("error", "Respuesta nula del servidor", QrData("ERROR CON EL SERVIDOR"))
            }
        }
    }

    suspend fun validateQr(token: String, qrCode: JSONObject): User? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(QrAPIClient::class.java).validateQR(token, qrCode)
            if (response.isSuccessful) {
                response.body() ?: null
            } else {
                null
            }
        }
    }
}