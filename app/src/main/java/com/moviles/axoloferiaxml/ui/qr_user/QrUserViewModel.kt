package com.moviles.axoloferiaxml.ui.qr_user

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.domain.GetQrUseCase

class QrUserViewModel: ViewModel() {

    private val getQrUseCase = GetQrUseCase()

    suspend fun generateQRCode(context: Context): Bitmap {
        val text = "Hello"
        val keystoreHelper = KeystoreHelper(context)
        val result = getQrUseCase(keystoreHelper)
        val encoder = BarcodeEncoder()
        if (result != null) {
            return encoder.encodeBitmap(result.data.qrCode, BarcodeFormat.QR_CODE, 500, 500)
        }else{
            return encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 500, 500)
        }
    }

}