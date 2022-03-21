package com.uguraltintas.sogumadanye.model

import com.google.gson.annotations.SerializedName

data class AddFood(
    @SerializedName("yemek_siparis_adet") var count: Int,
    @SerializedName("yemek_adi") val name: String,
    @SerializedName("yemek_resim_adi") val imageName: String,
    @SerializedName("yemek_fiyat") val price: Int,
    @SerializedName("kullanici_adi") val username : String
)
