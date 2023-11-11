package com.moviles.axoloferiaxml.data.model.user_employee

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class Employee(

    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val data: EmployeeData
){
    data class EmployeeData(
        @SerializedName("userInfo") @Expose val userInfo: Employee.EmployeeData.EmployeeInfo,
        @SerializedName("registered") @Expose val isRegistered: Boolean,
    ){
        data class EmployeeInfo(
            @Expose
            @SerializedName("email")
            val email: String,

            @Expose
            @SerializedName("id_rol")
            val idRol: Int,

            @Expose
            @SerializedName("user_name")
            val username: String,

            @Expose
            @SerializedName("coins")
            val coins: Int,

            @Expose
            @SerializedName("image_url")
            val imageUrl: String,

            @Expose
            @SerializedName("language_configured")
            val languageConfigured: String,
        )
    }

}
