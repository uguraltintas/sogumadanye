package com.uguraltintas.sogumadanye.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uguraltintas.sogumadanye.model.FoodFromCart
import com.uguraltintas.sogumadanye.model.User
import com.uguraltintas.sogumadanye.repository.CartRepository
import com.uguraltintas.sogumadanye.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository,
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _cartList = MutableLiveData<List<FoodFromCart>>()
    val cartList: LiveData<List<FoodFromCart>>
        get() = _cartList

    private val tempCartList: MutableList<FoodFromCart> = mutableListOf()

    private val _isCartListVisible = MutableLiveData<Boolean>()
    val isCartListVisible: LiveData<Boolean>
        get() = _isCartListVisible

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _isErrorVisible = MutableLiveData<Boolean>()
    val isErrorVisible: LiveData<Boolean>
        get() = _isErrorVisible

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _totalCost = MutableLiveData(0)
    val totalCost: LiveData<Int>
        get() = _totalCost

    private val _isAnonymous = MutableLiveData<Boolean>()
    val isAnonymous: LiveData<Boolean>
        get() = _isAnonymous

    fun deleteFood(food: FoodFromCart) {
        auth.currentUser?.let { outer ->
            repository.deleteFood(outer.email!!, food.id)
            tempCartList.remove(food)
            _cartList.value = tempCartList.toList()
            database.reference.child(outer.uid).child("user").get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                val cartCount = user!!.cartCount - food.count
                val newUser = user.copy(cartCount = cartCount)
                _totalCost.value = _totalCost.value?.minus(food.price * food.count)
                it.ref.setValue(newUser)
            }
        }
    }

    fun onOrderClick() {
        auth.currentUser?.let { outer ->
            val uidParent = database.reference.child(outer.uid).child("orders").push()
            uidParent.key?.let {
                val list = _cartList.value

                for (item in list!!) {
                    uidParent.ref.child("foods").child(item.id.toString()).setValue(item)
                    repository.deleteFood(outer.email!!, item.id)
                }
                uidParent.ref.child("status").setValue(0)

                val calendar = Calendar.getInstance()
                val time = calendar.timeInMillis
                uidParent.ref.child("time").setValue(time)

                _cartList.value = emptyList()
                _totalCost.value = 0

                database.reference.child(outer.uid).child("user").get().addOnSuccessListener {
                    val user = it.getValue(User::class.java)
                    val orderCount = user!!.orderCount
                    val newUser = user.copy(cartCount = 0, orderCount = orderCount + 1)
                    it.ref.setValue(newUser)
                }
            }
        }
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
                                for (food in it.foods) {
                                    _totalCost.value =
                                        _totalCost.value?.plus(food.count * food.price)
                                }
                                setProperties(
                                    isLoading = false,
                                    isErrorVisible = false,
                                    isCartListVisible = true
                                )
                            }
                        }
                        is Resource.Error -> {
                            outer.message?.let {
                                _error.postValue(it)
                                setProperties(
                                    isLoading = false,
                                    isErrorVisible = true,
                                    isCartListVisible = false
                                )
                            }
                        }
                        is Resource.Loading -> {
                            setProperties(
                                isLoading = true,
                                isErrorVisible = false,
                                isCartListVisible = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun isUserAnonymous() {
        auth.currentUser?.let {
            _isAnonymous.value = it.isAnonymous
        }
    }

    private fun setProperties(
        isLoading: Boolean,
        isErrorVisible: Boolean,
        isCartListVisible: Boolean
    ) {
        _loading.postValue(isLoading)
        _isErrorVisible.postValue(isErrorVisible)
        _isCartListVisible.postValue(isCartListVisible)
    }
}