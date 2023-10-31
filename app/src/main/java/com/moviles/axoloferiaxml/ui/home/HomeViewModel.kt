package com.moviles.axoloferiaxml.ui.home


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.domain.GetStallListUseCase
import com.moviles.axoloferiaxml.ui.home.adapters.StallAdapterListener
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){

    private val _stallResult = MutableLiveData<StallResult>()
    val stallResult: LiveData<StallResult> = _stallResult

    private val getStallListUseCase = GetStallListUseCase()

    fun getStallList(token: String, context: Context) {
        viewModelScope.launch {
            try {
                val result = getStallListUseCase(token)
                if (result != null) {
                    _stallResult.value = 
                        StallResult(success = result.stallData.let { StallView( stall = it!!) })
                    Log.d("xd", result.message)
                } else {
                    Log.d("xd", result?.message ?: "no hay")
                    Toast.makeText(context, "EXCEP$result", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "EXCEP", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}