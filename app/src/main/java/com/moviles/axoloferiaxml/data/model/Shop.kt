package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Shop(
    @Expose
    @SerializedName("status")
    val status: String,

    @Expose
    @SerializedName("msg")
    val message: String,

    @Expose
    @SerializedName("data")
    val data: ApiResponseData
)

data class ApiResponseData(
    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("uuid_client")
    val uuidClient: String,

    @Expose
    @SerializedName("id_payment_method")
    val paymentMethodId: Int,

    @Expose
    @SerializedName("cost")
    val cost: Int,

    @Expose
    @SerializedName("createdAt")
    val createdAt: String,

    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String
)
