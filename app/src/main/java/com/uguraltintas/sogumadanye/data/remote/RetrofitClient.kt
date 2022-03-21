package com.uguraltintas.sogumadanye.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitClient {

    companion object {

        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getRetrofit(): Retrofit {

            return INSTANCE ?: synchronized(this) {
                val instance = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://kasimadalan.pe.hu/")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}