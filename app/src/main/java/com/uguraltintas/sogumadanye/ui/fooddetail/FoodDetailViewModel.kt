package com.uguraltintas.sogumadanye.ui.fooddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uguraltintas.sogumadanye.model.AddFood
import com.uguraltintas.sogumadanye.model.Food
import com.uguraltintas.sogumadanye.model.FoodFromCart
import com.uguraltintas.sogumadanye.model.User
import com.uguraltintas.sogumadanye.repository.CartRepository
import com.uguraltintas.sogumadanye.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val repository: CartRepository,
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) :
    ViewModel() {

    private val _orderCount = MutableLiveData(0)
    val orderCount: LiveData<Int>
        get() = _orderCount

    private val _canOrder = MutableLiveData(false)
    val canOrder: LiveData<Boolean>
        get() = _canOrder

    private val _cartList = MutableLiveData<List<FoodFromCart>>()
    val cartList: LiveData<List<FoodFromCart>>
        get() = _cartList

    private val tempCartList: MutableList<FoodFromCart> = mutableListOf()

    fun clearCount() {
        _orderCount.value = 0
    }

    fun onIncreaseClick() {
        _orderCount.value = _orderCount.value?.plus(1)
        _canOrder.value = true
    }

    fun onReduceClick() {
        _orderCount.value?.let {
            if (it > 0) {
                _orderCount.value = it.minus(1)
                if (_orderCount.value == 0)
                    _canOrder.value = false
            } else
                _canOrder.value = false
        }
    }

    fun onOrderClick(food: Food) {
        auth.currentUser?.let { outer ->
            outer.email?.let {
                val addFood =
                    AddFood(_orderCount.value!!, food.name, food.imageName, food.price, it)
                addFoodToCart(addFood)
            }
        }
    }

    private fun addFoodToCart(food: AddFood) {
        for(item in tempCartList){
            if(item.name == food.name){
                deleteFood(item)
                food.count += item.count
            }
        }
        repository.addFoodToCart(food)
        auth.currentUser?.let { outer ->
            database.reference.child(outer.uid).child("user").get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                val cartCount = user!!.cartCount + _orderCount.value!!
                val newUser = user.copy(cartCount = cartCount)
                it.ref.setValue(newUser)
            }
        }
        getFoodFromCart()
    }

    fun getFoodFromCart() {
        viewModelScope.launch {
            if (auth.currentUser != null) {
                repository.getFoodFromCart(auth.currentUser!!.email!!).collect { outer ->
                    when (outer) {
                        is Resource.Success -> {
                            outer.data?.let { it ->
                                _cartList.postValue(it.foods)
                                if (tempCartList.isEmpty()) {
                                    tempCartList.addAll(it.foods)
                                } else {
                                    tempCartList.clear()
                                    tempCartList.addAll(it.foods)
                                }
                            }
                        }
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                    }
                }
            }
        }
    }

    private fun deleteFood(food: FoodFromCart) {
        auth.currentUser?.let { outer ->
            repository.deleteFood(outer.email!!, food.id)
            database.reference.child(outer.uid).child("user").get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                val cartCount = user!!.cartCount - food.count
                val newUser = user.copy(cartCount = cartCount)
                it.ref.setValue(newUser)
            }
        }
    }


}
