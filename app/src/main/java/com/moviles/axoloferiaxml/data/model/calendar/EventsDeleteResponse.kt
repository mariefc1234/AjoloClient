package com.moviles.axoloferiaxml.data.model.calendar

import com.google.gson.annotations.SerializedName

data class EventsDeleteResponse (

    @SerializedName("status" ) var status : String? = null,
    @SerializedName("msg"    ) var msg    : String? = null,
    @SerializedName("data"   ) var data   : Data?   = Data()
){
    data class Data (
        @SerializedName("event" ) var event : Int? = null
    )
}
