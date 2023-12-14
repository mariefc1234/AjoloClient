package com.moviles.axoloferiaxml.data.model.user_employee

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeDelete(
    @SerializedName("status") @Expose
    val status: String,
    @SerializedName("msg") @Expose
    val message: String,
    @SerializedName("data") @Expose
    val data: EmployeeListDeleted
) {
    data class EmployeeListDeleted(
        @SerializedName("user") @Expose val user: EmployeeDeleted?,
        @SerializedName("updated") @Expose val updated: String
    )
}

data class EmployeeDeleted(
    @SerializedName("username") @Expose val username: String,
    // Add other properties as needed
)

