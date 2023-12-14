package com.moviles.axoloferiaxml.data.model.user_employee

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeEdit (

    @Expose
    @SerializedName("username")
    var username: String,

    @Expose
    @SerializedName("email")
    var email: String,

)