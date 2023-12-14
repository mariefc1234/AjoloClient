package com.moviles.axoloferiaxml.data.model.sales

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SalePostResponse(

    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val data: SalePostResponseData

){
    data class SalePostResponseData (
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("uuid_client") @Expose val uuidClient: String,
        @SerializedName("uuid_employeer") @Expose val uuidEmployer: String,
        @SerializedName("id_payment_method") @Expose val idPaymentMethod: String,
        @SerializedName("cost") @Expose val cost: Int,
        @SerializedName("createdAt") @Expose val createdAt: String,
        @SerializedName("updatedAt") @Expose val updatedAt: String,
        @SerializedName("id_coupon") @Expose val idCoupon: Int,
    )
}