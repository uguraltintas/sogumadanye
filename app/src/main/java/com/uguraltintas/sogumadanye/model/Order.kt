package com.uguraltintas.sogumadanye.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val imageName: String,
    val id: String,
    val status: Int,
    val foodList: List<FoodFromCart>,
    val timestamp: Long,
    val cost: Int
) : Parcelable
