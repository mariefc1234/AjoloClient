package com.moviles.axoloferiaxml.ui.dashboard

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.moviles.axoloferiaxml.domain.GetQrUseCase

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val getQrUseCase = GetQrUseCase()

    /*fun generateqr() {
        val text = "Hello"
        val encoder = BarcodeEncoder()
        val bitmap = encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 500, 500)
        val myImageView: ImageView = binding.myImageView
        myImageView.setImageBitmap(bitmap)
    }*/

    suspend fun generateQRCode(token: String): Bitmap {
        val text = "Hello"
        val result = getQrUseCase(token)
        val encoder = BarcodeEncoder()
        if (result != null) {
            return encoder.encodeBitmap(result.data.qrCode, BarcodeFormat.QR_CODE, 500, 500)
        }else{
            return encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 500, 500)
        }
    }

    /*fun generateQRCode(token: String){
        val text = "Hello"
        val encoder = BarcodeEncoder()
        val bitmap = encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 500, 500)
        val myImageView:    ImageView = binding.myImageView
        myImageView.setImageBitmap(bitmap)
    }*/
}