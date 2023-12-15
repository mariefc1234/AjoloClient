package com.moviles.axoloferiaxml.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.databinding.ActivityLoginBinding
import com.moviles.axoloferiaxml.ui.register.RegisterActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val username = binding.emailEditText
        val password = binding.passwordEditText
        val login = binding.loginbtn
        val loading = binding.loading
        val register = binding.registerTextView

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                saveIdInPreferences(loginResult.success.user)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString(),
                            this@LoginActivity
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString(), this@LoginActivity)
            }

            register.setOnClickListener{
                loading.visibility = View.VISIBLE
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveIdInPreferences(user: User.UserData.UserInfo) {
        val preference = "SOM"

        val prefs = getSharedPreferences(preference, MODE_PRIVATE)
        val name = prefs.getString("uuid", "NULL") ?: "NULL"
        Log.d("uuid", name)

        val editor = getSharedPreferences(preference, MODE_PRIVATE).edit()
        editor.remove("uuid")
        editor.apply()
        Log.d("user id", user.uuid)
        editor.putString("uuid", user.uuid)
        editor.apply()

        val prefs2 = getSharedPreferences(preference, MODE_PRIVATE)
        val name2 = prefs2.getString("uuid", "NULL") ?: "NULL"
        Log.d("uuid", name2)
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = binding.emailEditText.text.toString()
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName ",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
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
