package com.moviles.axoloferiaxml.data.model.offers

import com.google.gson.annotations.SerializedName


data class OffersPost (

    @SerializedName("id_coupon_type" ) var idCouponType  : Int?    = null,
    @SerializedName("value_coupon"   ) var valueCoupon   : Int?    = null,
    @SerializedName("code_coupon"    ) var codeCoupon    : String? = null,
    @SerializedName("minimun_amount" ) var minimunAmount : Int?    = null,
    @SerializedName("uses_per_user"  ) var usesPerUser   : Int?    = null,
    @SerializedName("total_uses"     ) var totalUses     : Int?    = null

)