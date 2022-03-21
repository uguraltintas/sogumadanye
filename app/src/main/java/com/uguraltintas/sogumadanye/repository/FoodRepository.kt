package com.uguraltintas.sogumadanye.repository

import com.uguraltintas.sogumadanye.data.remote.FoodService
import com.uguraltintas.sogumadanye.model.AddFood
import com.uguraltintas.sogumadanye.model.FoodResponse
import com.uguraltintas.sogumadanye.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FoodRepository @Inject constructor(private val service: FoodService) {

    @ExperimentalCoroutinesApi
    fun getAllFoods(): Flow<Resource<FoodResponse>> {
        return callbackFlow {
            trySend(Resource.Loading())
            val call = service.getAllFoods()
            call.enqueue(object : Callback<FoodResponse> {
                override fun onResponse(
                    call: Call<FoodResponse>,
                    response: Response<FoodResponse>
                ) {
                    val data = response.body()
                    data?.let {
                        trySend(Resource.Success(it))
                    }
                }

                override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                    val message = t.message
                    message?.let {
                        trySend(Resource.Error(it))
                    }
                }
            })
            awaitClose { call.cancel() }
        }
    }


}