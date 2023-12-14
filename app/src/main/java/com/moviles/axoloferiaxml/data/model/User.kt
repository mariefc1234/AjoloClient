package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val userData: UserData?
) {
    data class UserData(
        @SerializedName("userInfo") @Expose val userInfo: UserInfo,
        @SerializedName("registered") @Expose val isRegistered: Boolean,
        @SerializedName("token") @Expose val token: String
    ) {
        data class UserInfo(
            @SerializedName("uuid") @Expose val uuid: String,
            @SerializedName("email") @Expose val email: String,
            @SerializedName("id_rol") @Expose val roleId: Int,
            @SerializedName("user_name") @Expose val userName: String,
            @SerializedName("coins") @Expose val coins: Double,
            @SerializedName("image_url") @Expose val imageUrl: String,
            @SerializedName("language_configured") @Expose val languageConfigured: String
        )
    }
}