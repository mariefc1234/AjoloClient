package com.moviles.axoloferiaxml.ui.sale_coins

import com.moviles.axoloferiaxml.data.model.User

data class QrResult (
    val success: User.UserData.UserInfo? = null,
    val error: Int? = null
)