package com.moviles.axoloferiaxml.ui.register

import com.moviles.axoloferiaxml.ui.login.LoggedInUserView

data class RegisterResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)