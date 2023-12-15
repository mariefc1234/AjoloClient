package com.moviles.axoloferiaxml.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coupon(
    @Expose
    @SerializedName("status")
    val status: String,

    @Expose
    @SerializedName("msg")
    val message: String,

    @Expose
    @SerializedName("data")
    val data: CouponData
)

data class CouponData(
    @Expose
    @SerializedName("valid")
    val isValid: Boolean,

    @Expose
    @SerializedName("id_coupon_type")
    val couponType: String,

    @Expose
    @SerializedName("minimun_amount")
    val minimumAmount: Int,

    @Expose
    @SerializedName("value_coupon")
    val couponValue: Int,

    @Expose
    @SerializedName("id_coupon")
    val couponId: Int
)
