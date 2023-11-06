package com.moviles.axoloferiaxml.ui.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.UserAuth
import com.moviles.axoloferiaxml.domain.GetAuthenticationUserUseCase
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val getAuthenticationUseCase = GetAuthenticationUserUseCase()

    fun login(username: String, password: String, context: Context) {
        viewModelScope.launch {
            try {
                val userAuth = UserAuth(username, password)
                val result = getAuthenticationUseCase(userAuth)
                if (result != null) {
                    val userInfo = result.userData?.userInfo
                    if (userInfo != null) {
                        when (userInfo.roleId) {
                            2 -> {
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("userName", userInfo.userName)
                                context.startActivity(intent)
                            }
                            4 -> {
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("userName", userInfo.userName)
                                context.startActivity(intent)

                            }
                            else -> {
                                Toast.makeText(context, "Rol desconocido", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Error en la respuesta del servidor", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("MiTag", "Error en la autenticación", e)
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (!username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}