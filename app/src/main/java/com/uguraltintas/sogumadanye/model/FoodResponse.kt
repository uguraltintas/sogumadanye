package com.uguraltintas.sogumadanye.model

import com.google.gson.annotations.SerializedName

data class FoodResponse(@SerializedName("yemekler") val foods: List<Food>, val success: Int)
