package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Stall(
    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val stallData: MutableList<StallData>?
) {
    data class StallData(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("id_stall_type") @Expose val id_stall_type: String,
        @SerializedName("name") @Expose val name: String,
        @SerializedName("description") @Expose val description: String,
        @SerializedName("image_url") @Expose val image_url: String,
        @SerializedName("cost") @Expose val cost: String,
        @SerializedName("minimun_height_cm") @Expose val minimun_height_cm: String,
        @SerializedName("enabled") @Expose val enabled: String,
        @SerializedName("createdAt") @Expose val createdAt: String,
        @SerializedName("updatedAt") @Expose val updatedAt: String
    )

}