package com.moviles.axoloferiaxml.data.model.charges

import com.google.gson.annotations.SerializedName


data class ChargePostResponse (

    @SerializedName("status" ) var status : String? = null,
    @SerializedName("msg"    ) var msg    : String? = null,
    @SerializedName("data"   ) var data   : Data?   = Data()

){

    data class Data (

        @SerializedName("id"             ) var id            : String? = null,
        @SerializedName("uuid_client"    ) var uuidClient    : String? = null,
        @SerializedName("uuid_employeer" ) var uuidEmployeer : String? = null,
        @SerializedName("cost"           ) var cost          : Int?    = null,
        @SerializedName("type"           ) var type          : String? = null,
        @SerializedName("createdAt"      ) var createdAt     : String? = null,
        @SerializedName("updatedAt"      ) var updatedAt     : String? = null

    )
}