package com.moviles.axoloferiaxml.data.model.calendar

import com.google.gson.annotations.SerializedName


data class EventsPostResponse (
    @SerializedName("status" ) var status : String? = null,
    @SerializedName("msg"    ) var msg    : String? = null,
    @SerializedName("data"   ) var data   : Data?   = Data()
){
    data class Data (
        @SerializedName("event" ) var event : Event? = Event()
    ){
        data class Event (
            @SerializedName("id"        ) var id        : String? = null,
            @SerializedName("uuid"      ) var uuid      : String? = null,
            @SerializedName("name"      ) var name      : String? = null,
            @SerializedName("cost"      ) var cost      : Int?    = null,
            @SerializedName("dateEvent" ) var dateEvent : String? = null,
            @SerializedName("location"  ) var location  : String? = null,
            @SerializedName("timeEvent" ) var timeEvent : String? = null,
            @SerializedName("createdAt" ) var createdAt : String? = null,
            @SerializedName("updatedAt" ) var updatedAt : String? = null
        )
    }


}