package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.cardShop

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.Card
import com.moviles.axoloferiaxml.domain.GetCardUseCase
import kotlinx.coroutines.launch

class CardViewModel: ViewModel() {

    private val getCardUseCase = GetCardUseCase()

    private val _cardLiveData = MutableLiveData<Card?>()
    val cardLiveData: MutableLiveData<Card?> get() = _cardLiveData

    fun getCard(context: Context) {
        viewModelScope.launch {
            try {
                val keystoreHelper = KeystoreHelper(context)
                val result = getCardUseCase(keystoreHelper)
                // Actualiza el LiveData con la información de la tarjeta
                _cardLiveData.postValue(result)
            } catch (e: Exception) {
                // Manejo de errores
                e.printStackTrace()
            }
        }
    }

}