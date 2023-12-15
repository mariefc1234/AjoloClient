package com.moviles.axoloferiaxml.data.model.offers


import com.google.gson.annotations.SerializedName


data class OffersPostResponse (
    @SerializedName("status" ) var status : String? = null,
    @SerializedName("msg"    ) var msg    : String? = null,
    @SerializedName("data"   ) var data   : Data?   = Data()
){
    data class Data (
        @SerializedName("registered" ) var registered : Boolean? = null
    )
}
