package com.uguraltintas.sogumadanye.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email : String = "",
    val name : String = "",
    val cartCount : Int = 0,
    val orderCount : Int = 0
) : Parcelable
