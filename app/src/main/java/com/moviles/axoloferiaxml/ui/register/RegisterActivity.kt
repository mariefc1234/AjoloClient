package com.moviles.axoloferiaxml.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.data.model.RegisterAuth
import com.moviles.axoloferiaxml.data.model.RegisterUser
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.network.user_employee.RetrofitClient
import com.moviles.axoloferiaxml.databinding.ActivityRegisterBinding
import com.moviles.axoloferiaxml.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val username = binding.registerUsernameEditText
        val email = binding.registerEmailEditText
        val password = binding.registerPasswordTextInput
        val passwordConfirmed = binding.registerPasswordConfirmedTextInput
        val register = binding.registerButton

        register.isEnabled = false

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            // disable register button unless both username / password is valid
            register.isEnabled = registerState.isDataValid

            if (registerState.emailError != null) {
                email.error = getString(registerState.emailError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
            if (registerState.confirmPasswordError != null) {
                passwordConfirmed.error = getString(registerState.confirmPasswordError)
            }
        })

        username.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                passwordConfirmed.text.toString()
            )
        }

        email.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                passwordConfirmed.text.toString()
            )
        }

        password.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                passwordConfirmed.text.toString()
            )
        }

        passwordConfirmed.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                passwordConfirmed.text.toString()
            )
        }

        register.setOnClickListener {
            register(username.text.toString(), email.text.toString(), password.text.toString(), passwordConfirmed.text.toString(), 1)
        }

        binding.loginText.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun register(username: String, email: String, password: String, passwordConfirmed: String, rol: Int){

        val user = RegisterAuth(email, password, username, rol)
        val result = registerViewModel.onCreate(user)

        if(result != null) {
            Toast.makeText(this, "Usuario registrado!", Toast.LENGTH_SHORT).show()
            goToLogin()
        } else {
            Toast.makeText(this, "Error en registro!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun goToLogin(){
        val loading = binding.loading
        loading.visibility = View.VISIBLE
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}