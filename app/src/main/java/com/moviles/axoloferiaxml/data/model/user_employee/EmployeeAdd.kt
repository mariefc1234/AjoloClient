package com.moviles.axoloferiaxml.data.model.user_employee

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeAdd (
    @Expose
    @SerializedName("email")
    var email: String,

    @Expose
    @SerializedName("password")
    var ps: String,

    @Expose
    @SerializedName("username")
    var username: String,

    @Expose
    @SerializedName("id_rol")
    var idRol: Int
)