package com.moviles.axoloferiaxml.data.model.calendar

import com.google.gson.annotations.SerializedName

data class EventsResponse (
    @SerializedName("status") var status : String,
    @SerializedName("msg") var msg : String,
    @SerializedName("data") var data : Data

){
    data class Data (
        @SerializedName("event" ) var event : ArrayList<Event> = arrayListOf()
    ){
        data class Event (
            @SerializedName("id") var id : Int,
            @SerializedName("uuid") var uuid : String,
            @SerializedName("name") var name : String,
            @SerializedName("cost") var cost : Int,
            @SerializedName("dateEvent") var dateEvent : String,
            @SerializedName("location") var location  : String,
            @SerializedName("timeEvent") var timeEvent  : String,
            @SerializedName("createdAt") var createdAt : String,
            @SerializedName("updatedAt") var updatedAt : String
        )
    }
}