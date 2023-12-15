package com.moviles.axoloferiaxml.data.model.charges

import com.google.gson.annotations.SerializedName

data class ChargePost (

    @SerializedName("uuid_client" ) var uuidClient : String? = null,
    @SerializedName("coins"       ) var coins      : Int?    = null

)
