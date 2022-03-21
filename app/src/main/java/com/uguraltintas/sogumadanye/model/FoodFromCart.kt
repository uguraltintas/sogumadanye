package com.uguraltintas.sogumadanye.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodFromCart(
    @SerializedName("sepet_yemek_id") val id: Int = 0,
    @SerializedName("yemek_adi") val name: String = "",
    @SerializedName("yemek_resim_adi") val imageName: String = "",
    @SerializedName("yemek_fiyat") val price: Int = 0,
    @SerializedName("yemek_siparis_adet") val count: Int = 0,
    @SerializedName("kullanici_adi") val username : String = ""
) : Parcelable
