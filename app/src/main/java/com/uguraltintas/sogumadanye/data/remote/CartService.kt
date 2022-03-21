package com.uguraltintas.sogumadanye.data.remote

import com.uguraltintas.sogumadanye.model.CRUDResponse
import com.uguraltintas.sogumadanye.model.FoodFromCartResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CartService {

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    fun addFoodToCart(
        @Field("yemek_siparis_adet") count: Int,
        @Field("yemek_adi") name: String,
        @Field("yemek_resim_adi") imageName: String,
        @Field("yemek_fiyat") price: Int,
        @Field("kullanici_adi") username: String
    ): Call<CRUDResponse>

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun getFoodFromCart(@Field("kullanici_adi") username: String): Call<FoodFromCartResponse>

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    fun deleteFood(
        @Field("kullanici_adi") username: String,
        @Field("sepet_yemek_id") id: Int
    ): Call<CRUDResponse>


}