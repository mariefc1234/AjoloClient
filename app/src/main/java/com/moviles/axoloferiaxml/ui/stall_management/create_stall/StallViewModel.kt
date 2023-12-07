package com.moviles.axoloferiaxml.ui.stall_management.create_stall

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.StallCreate
import com.moviles.axoloferiaxml.domain.CreateStallUseCase
import com.moviles.axoloferiaxml.domain.GetUsersByRoleListUseCase
import com.moviles.axoloferiaxml.ui.stall_management.choose_stall_holder.adapters.StallHolderResult
import kotlinx.coroutines.launch

class StallViewModel: ViewModel() {
    private val _stallResult = MutableLiveData<GenericResponseResult>()
    val stallResult: LiveData<GenericResponseResult> = _stallResult

    private val _stallHolderResult = MutableLiveData<StallHolderResult>()
    val stallHolderResult: LiveData<StallHolderResult> = _stallHolderResult

    private val createStallUseCase = CreateStallUseCase()
    private val getUsersByRoleListUseCase = GetUsersByRoleListUseCase()

    fun createStall(stall: StallCreate, context: Context) {
        viewModelScope.launch {
            try {
                val keystoreHelper = KeystoreHelper(context)
                val result = createStallUseCase(stall, keystoreHelper)
                if (result != null) {
                    _stallResult.value =
                        GenericResponseResult(success = result)
                } else {
                    _stallResult.value =
                        GenericResponseResult(error = 0)
                    Toast.makeText(context, "EXCEP$result", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "EXCEP", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

    fun getStallHolders(context: Context) {
        viewModelScope.launch {
            try {
                val keystoreHelper = KeystoreHelper(context)
                val result = getUsersByRoleListUseCase(3, keystoreHelper)
                if (result != null) {
                    _stallHolderResult.value =
                        StallHolderResult(success = result)

                } else {
                    _stallHolderResult.value =
                        StallHolderResult(error = 0)
                    Toast.makeText(context, "EXCEP$result", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "EXCEP", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

//    fun stallDataChanged(username: String, password: Int) {
//        if (!isTextValid(username)) {
//            _stallResult.value = StallFormState(textInput = "Campo de texto inválido")
//        } else {
//            _stallResult.value = StallFormState(isDataValid = true)
//        }
//    }
//
//    private fun isNumberValid(number: Int): Any {
//
//    }
//
//    private fun isTextValid(text: String): Any {
//
//    }
}