package com.moviles.axoloferiaxml.data.model.calendar

import com.google.gson.annotations.SerializedName

data class EventsPost (
    @SerializedName("name") var name : String,
    @SerializedName("cost") var cost : Int,
    @SerializedName("dateEvent") var dateEvent : String,
    @SerializedName("location") var location  : String,
    @SerializedName("timeEvent") var timeEvent  : String
)