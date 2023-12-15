package com.moviles.axoloferiaxml.ui.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.RegisterAuth
import com.moviles.axoloferiaxml.data.model.RegisterUser
import com.moviles.axoloferiaxml.domain.RegisterClientUseCase
import com.moviles.axoloferiaxml.ui.login.LoggedInUserView
import com.moviles.axoloferiaxml.ui.login.LoginActivity
import com.moviles.axoloferiaxml.ui.login.LoginResult
import com.moviles.axoloferiaxml.ui.login.LoginViewModel
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val registerModel = MutableLiveData<String?>()
    val registerUseCase = RegisterClientUseCase()

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun onCreate(user: RegisterAuth) {
        viewModelScope.launch {
            val result = registerUseCase(user)

            if(result != null) {
                //registerModel.postValue(result)
            }
        }
    }
    fun registerDataChanged(username: String, email: String, password: String, confirmPassword: String) {
        if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (!isConfirmPasswordValid(password, confirmPassword)) {
            _registerForm.value = RegisterFormState(confirmPasswordError = R.string.match_password)
        }else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isConfirmPasswordValid(password1: String, password2: String): Boolean {
        return password1 == password2
    }

    private fun isEmailValid(username: String): Boolean {
        return if (!username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,}\$")
        return regex.matches(password)
    }
}