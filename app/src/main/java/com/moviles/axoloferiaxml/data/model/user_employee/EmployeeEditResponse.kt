package com.moviles.axoloferiaxml.data.model.user_employee

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeEditResponse(

    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val data: EmployeeEditResponseData
){
    data class EmployeeEditResponseData (
        @Expose
        @SerializedName("user_name")
        val username: String,

        @Expose
        @SerializedName("email")
        val email: String,

    )
}