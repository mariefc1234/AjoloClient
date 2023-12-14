package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterUser(
    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val userData: UserData?
) {
    data class UserData(
        @SerializedName("registered") @Expose val isRegistered: Boolean,
    )
}