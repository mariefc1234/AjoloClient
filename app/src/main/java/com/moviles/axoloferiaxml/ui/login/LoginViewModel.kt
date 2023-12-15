package com.moviles.axoloferiaxml.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.MainActivityStall
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.User
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
                val keystoreHelper = KeystoreHelper(context)
                val result = getAuthenticationUseCase(userAuth, keystoreHelper)
                if (result != null) {
                    _loginResult.value =
                        LoginResult(success = result.userData?.userInfo?.let { LoggedInUserView(displayName = it.userName, user = it) })




                    val intent = Intent(context, MainActivity::class.java)

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                    val userInfo = result.userData?.userInfo
                    if (userInfo != null) {
                        when (userInfo.roleId) {
                            1 -> {
                                val intent = Intent(context, MainActivityUser::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("userName", userInfo.userName)
                                intent.putExtra("uuid", userInfo.uuid)
                                intent.putExtra("coins", userInfo.coins.toString())
                                context.startActivity(intent)
                            }
                            //admin
                            2 -> {
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("userName", userInfo.userName)
                                intent.putExtra("role", userInfo.roleId.toString())
                                context.startActivity(intent)
                            }
                            3 -> {
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("userName", userInfo.userName)
                                intent.putExtra("role", userInfo.roleId.toString())
                                context.startActivity(intent)
                            }
                            //encargada de puesto
                            4 -> {
                                val intent = Intent(context, MainActivityStall::class.java)
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
                // Manejar errores si es necesario, por ejemplo, mostrar un mensaje de error
                //_loginResult.value = LoginResult(error = R.string.login_failed)
                Toast.makeText(context, "EXCEP login", Toast.LENGTH_LONG).show()
                e.printStackTrace()
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