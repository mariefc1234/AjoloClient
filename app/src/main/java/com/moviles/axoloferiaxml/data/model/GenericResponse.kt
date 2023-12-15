package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenericResponse (
    @SerializedName("status") @Expose
    val status: String,

    @SerializedName("msg") @Expose
    val message: String,

    @SerializedName("data") @Expose
    val data: Data
) {
    data class Data (
        @SerializedName("created") @Expose
        val created: Boolean,
    )
}