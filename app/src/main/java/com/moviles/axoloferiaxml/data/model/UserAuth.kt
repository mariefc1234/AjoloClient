package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserAuth (
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("password")
    val password: String
)