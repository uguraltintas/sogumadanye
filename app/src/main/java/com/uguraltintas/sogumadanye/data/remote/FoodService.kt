package com.uguraltintas.sogumadanye.data.remote

import com.uguraltintas.sogumadanye.model.FoodResponse
import retrofit2.Call
import retrofit2.http.GET

interface FoodService {

    @GET("yemekler/tumYemekleriGetir.php")
    fun getAllFoods(): Call<FoodResponse>
}