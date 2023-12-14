package com.moviles.axoloferiaxml.data.model.user_employee

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeEditFields (
    @Expose
    @SerializedName("email")
    var email: String,

    @Expose
    @SerializedName("username")
    var username: String,

    @Expose
    @SerializedName("uuid")
    var uuid: String,

    )