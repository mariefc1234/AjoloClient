package com.moviles.axoloferiaxml.data.model.sales

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SalePost(
    @Expose
    @SerializedName("uuidClient")
    var uuidClient: String,

    @Expose
    @SerializedName("idCoupon")
    var idCoupon: Int,

    @Expose
    @SerializedName("cost")
    var cost: Int
)
