package com.moviles.axoloferiaxml.data.model.offers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OfferGet(

    @SerializedName("status") @Expose val status: String,
    @SerializedName("msg") @Expose val message: String,
    @SerializedName("data") @Expose val data: OfferGetData

){
    data class OfferGetData(
        @Expose
        @SerializedName("valid")
        val valid: Boolean,

        @Expose
        @SerializedName("id_coupon_type")
        val idCouponType: String,

        @Expose
        @SerializedName("minimun_amount")
        val minimunAmount: Int,

        @Expose
        @SerializedName("value_coupon")
        val valueCoupon: Int,

        @Expose
        @SerializedName("id_coupon")
        val id_coupon: Int,
    )
}