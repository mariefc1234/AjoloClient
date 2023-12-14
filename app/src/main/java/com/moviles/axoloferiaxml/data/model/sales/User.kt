package com.moviles.axoloferiaxml.data.model.sales

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val data: UserData

){
    data class UserData(
        @Expose
        @SerializedName("valid")
        val valid: Boolean,

        @Expose
        @SerializedName("uuid")
        var uuid: String,

        @Expose
        @SerializedName("username")
        val username: String,

        @Expose
        @SerializedName("coins")
        val coins: Int,

        @Expose
        @SerializedName("image_url")
        val imageUrl: String,

        @Expose
        @SerializedName("email")
        val email: String,

    )
}