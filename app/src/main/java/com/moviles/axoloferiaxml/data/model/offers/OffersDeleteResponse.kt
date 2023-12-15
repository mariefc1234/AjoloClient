package com.moviles.axoloferiaxml.data.model.offers


import com.google.gson.annotations.SerializedName

data class OffersDeleteResponse(

    @SerializedName("status" ) var status : String? = null,
    @SerializedName("msg"    ) var msg    : String? = null,
    @SerializedName("data"   ) var data   : Data?   = Data()
){

    data class Data(
        @SerializedName("deleted") var deleted    : Boolean? = null,
    )
}
