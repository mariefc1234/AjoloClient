package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Qr (
    @Expose
    @SerializedName("status")
    val status: String,

    @Expose
    @SerializedName("msg")
    val message: String,

    @Expose
    @SerializedName("data")
    val data: QrData
)

data class QrData(
    @Expose
    @SerializedName("qrCode")
    val qrCode: String
)