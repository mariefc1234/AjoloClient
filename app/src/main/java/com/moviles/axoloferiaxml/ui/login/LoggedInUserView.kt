package com.moviles.axoloferiaxml.ui.login

import com.moviles.axoloferiaxml.data.model.User

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val user: User.UserData.UserInfo
    //... other data fields that may be accessible to the UI
)