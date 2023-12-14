package com.moviles.axoloferiaxml.data.model.user_employee

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.moviles.axoloferiaxml.data.model.Stall

data class Employee(

    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val data: EmployeeList
){
    data class EmployeeList (
        @SerializedName("users") @Expose val users: MutableList<EmployeeInfo>?
    ){
        data class EmployeeInfo(
            @Expose
            @SerializedName("uuid")
            var uuid: String,

            @Expose
            @SerializedName("email")
            val email: String,

            @Expose
            @SerializedName("ps")
            val ps: String,

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

            @Expose
            @SerializedName("enabled")
            val enabled: Int,

            @Expose
            @SerializedName("id_rol")
            val idRol: Int,

            @Expose
            @SerializedName("createdAt")
            val createdAt: String,

            @Expose
            @SerializedName("updatedAt")
            val updatedAt: String,
        )
    }

}
