package com.uguraltintas.sogumadanye.repository

import android.util.Log
import com.uguraltintas.sogumadanye.data.remote.CartService
import com.uguraltintas.sogumadanye.model.AddFood
import com.uguraltintas.sogumadanye.model.CRUDResponse
import com.uguraltintas.sogumadanye.model.FoodFromCartResponse
import com.uguraltintas.sogumadanye.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CartRepository @Inject constructor(private val service: CartService) {

    fun addFoodToCart(food: AddFood) {
        val call = service.addFoodToCart(food.count,food.name,food.imageName,food.price,food.username)
        call.enqueue(object : Callback<CRUDResponse>{
            override fun onResponse(call: Call<CRUDResponse>, response: Response<CRUDResponse>) {
                response.body()?.let {
                    Log.e("foodFromCart",it.toString())
                }

            }

            override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                t.message?.let {
                    Log.e("foodFromCartErr",it)
                }
            }

        })
    }

    @ExperimentalCoroutinesApi
    fun getFoodFromCart(username: String): Flow<Resource<FoodFromCartResponse>> {
        return callbackFlow {
            trySend(Resource.Loading())
            val response = service.getFoodFromCart(username)
            response.enqueue(object : Callback<FoodFromCartResponse>{
                override fun onResponse(
                    call: Call<FoodFromCartResponse>,
                    response: Response<FoodFromCartResponse>
                ) {
                    val data = response.body()
                    data?.let {
                        trySend(Resource.Success(it))
                    }
                }

                override fun onFailure(call: Call<FoodFromCartResponse>, t: Throwable) {
                    t.message?.let{
                        trySend(Resource.Error(it))
                    }
                }

            })
            awaitClose { response.cancel() }
        }

    }

    fun deleteFood(username: String, id: Int) {
        val call = service.deleteFood(username,id)
        call.enqueue(object : Callback<CRUDResponse>{
            override fun onResponse(call: Call<CRUDResponse>, response: Response<CRUDResponse>) {
                response.body()?.let {
                    Log.e("deleteFoodS",it.toString())
                }

            }

            override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                t.message?.let {
                    Log.e("deleteFoodErr",it)
                }
            }

        })
    }
}