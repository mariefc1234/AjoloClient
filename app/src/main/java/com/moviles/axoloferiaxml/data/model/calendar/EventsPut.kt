package com.moviles.axoloferiaxml.data.model.calendar

import com.google.gson.annotations.SerializedName


data class EventsPut (

    @SerializedName("id" ) var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("cost") var cost : Int,
    @SerializedName("dateEvent") var dateEvent : String,
    @SerializedName("location" ) var location  : String

)