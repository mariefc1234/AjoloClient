package com.moviles.axoloferiaxml.ui.sale_coins

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.domain.ValidateQrUseCase
import com.moviles.axoloferiaxml.ui.home_user.StallResult
import com.moviles.axoloferiaxml.ui.home_user.StallView
import kotlinx.coroutines.launch
import org.json.JSONObject

class SalesCoinsViewModel: ViewModel() {

    private val _qrResult = MutableLiveData<QrResult>()
    val qrResult: LiveData<QrResult> = _qrResult

    private val validateQrUseCase = ValidateQrUseCase()

    fun ValidateQR(context: Context, qrCode: JSONObject) {
        viewModelScope.launch {
            try {
                val keystoreHelper = KeystoreHelper(context)
                val result = validateQrUseCase(keystoreHelper, qrCode)
                if (result != null) {
                    _qrResult.value =
                        QrResult(success = result.userData?.userInfo )
                    Log.d("User",_qrResult.value?.success?.userName ?: "no jala")
                } else {
                    _qrResult.value =
                        QrResult(error = 0)
                    Toast.makeText(context, "EXCEP$result", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, "EXCEP", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }

    }
}