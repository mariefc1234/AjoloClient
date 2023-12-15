package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class RegisterAuth (
    @Expose
    @SerializedName("email")
    var email: String,

    @Expose
    @SerializedName("password")
    var password: String,

    @Expose
    @SerializedName("username")
    var username: String,

    @Expose
    @SerializedName("rol")
    var idRol: Int
)