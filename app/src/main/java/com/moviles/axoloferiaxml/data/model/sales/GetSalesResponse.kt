package com.moviles.axoloferiaxml.data.model.sales

import com.google.gson.annotations.SerializedName

data class GetSalesResponse (
    @SerializedName("status") var status : String,
    @SerializedName("msg") var msg : String,
    @SerializedName("data") var data : Data

){
    data class Data (
        @SerializedName("shoppings") var shoppings : ArrayList<Shoppings> = arrayListOf()
    ){
        data class Shoppings (
            @SerializedName("payment_method") var paymentMethod : Int,
            @SerializedName("cost") var cost : Double,
            @SerializedName("coins") var coins : Double,
            @SerializedName("couponName") var couponName : String,
            @SerializedName("createdAt") var createdAt : String,
        )
    }
}