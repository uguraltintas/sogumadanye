package com.uguraltintas.sogumadanye.model

import com.google.gson.annotations.SerializedName

data class FoodFromCartResponse(
    @SerializedName("sepet_yemekler") val foods: List<FoodFromCart>,
    val success: Int
)