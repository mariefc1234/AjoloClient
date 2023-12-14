package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Review (
    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val reviewData: MutableList<ReviewData>?
) {
    data class ReviewData(
        @SerializedName("id") @Expose val id: Int?,
        @SerializedName("id_stall") @Expose val idStall: Int,
        @SerializedName("description") @Expose val description: String,
        @SerializedName("points") @Expose val points: Double,
        @SerializedName("createdAt") @Expose val createdAt: String?,
        @SerializedName("updatedAt") @Expose val updatedAt: String?,
        @SerializedName("publisher") @Expose val publisher: User.UserData.UserInfo?,
    )
}

class PublishReview(
    @SerializedName("idStall") @Expose val idStall: Int,
    @SerializedName("description") @Expose val description: String,
    @SerializedName("points") @Expose val points: Double,
)