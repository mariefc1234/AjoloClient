package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Card(
    @Expose
    @SerializedName("status")
    val status: String,

    @Expose
    @SerializedName("msg")
    val message: String,

    @Expose
    @SerializedName("data")
    val data: CardData
)

data class CardData(
    @Expose
    @SerializedName("cardExist")
    val cardExist: Boolean,

    @Expose
    @SerializedName("card")
    val card: CardDetails
)

data class CardDetails(
    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("uuid_client")
    val uuidClient: String,

    @Expose
    @SerializedName("card_number")
    val cardNumber: String,

    @Expose
    @SerializedName("card_holder")
    val cardHolder: String,

    @Expose
    @SerializedName("card_expiration_month")
    val cardExpirationMonth: Int,

    @Expose
    @SerializedName("card_expiration_year")
    val cardExpirationYear: Int,

    @Expose
    @SerializedName("normalized_name")
    val normalizedName: String,

    @Expose
    @SerializedName("enabled")
    val enabled: Int,

    @Expose
    @SerializedName("createdAt")
    val createdAt: String,

    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String
)
