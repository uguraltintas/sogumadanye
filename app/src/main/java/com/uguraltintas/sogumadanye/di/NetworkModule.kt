package com.uguraltintas.sogumadanye.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uguraltintas.sogumadanye.data.remote.CartService
import com.uguraltintas.sogumadanye.data.remote.FoodService
import com.uguraltintas.sogumadanye.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit = RetrofitClient.getRetrofit()

    @Singleton
    @Provides
    fun providesFoodService(retrofit: Retrofit) : FoodService = retrofit.create(FoodService::class.java)

    @Singleton
    @Provides
    fun providesCartService(retrofit: Retrofit) : CartService = retrofit.create(CartService::class.java)

    @Singleton
    @Provides
    fun providesFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesFirebaseDatabase() : FirebaseDatabase = FirebaseDatabase.getInstance()
}