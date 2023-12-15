package com.moviles.axoloferiaxml.data.model.offers

import com.google.gson.annotations.SerializedName

data class OffersGetAllsResponse (
    @SerializedName("status" ) var status : String?         = null,
    @SerializedName("msg"    ) var msg    : String?         = null,
    @SerializedName("data"   ) var data   : ArrayList<Data> = arrayListOf()
){
    data class Data (
        @SerializedName("id"             ) var id            : Int?     = null,
        @SerializedName("id_coupon_type" ) var idCouponType  : Int?     = null,
        @SerializedName("code_coupon"    ) var codeCoupon    : String?  = null,
        @SerializedName("minimun_amount" ) var minimunAmount : Int?     = null,
        @SerializedName("value_coupon"   ) var valueCoupon   : Int?     = null,
        @SerializedName("uses_per_user"  ) var usesPerUser   : Int?     = null,
        @SerializedName("total_uses"     ) var totalUses     : Int?     = null,
        @SerializedName("enabled"        ) var enabled       : Boolean? = null,
        @SerializedName("createdAt"      ) var createdAt     : String?  = null,
        @SerializedName("updatedAt"      ) var updatedAt     : String?  = null

    )
}
