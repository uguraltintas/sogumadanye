package com.uguraltintas.sogumadanye.ui.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uguraltintas.sogumadanye.model.Food
import com.uguraltintas.sogumadanye.repository.FoodRepository
import com.uguraltintas.sogumadanye.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) :
    ViewModel() {

    private val _foodList = MutableLiveData<List<Food>>()
    val foodList: LiveData<List<Food>>
        get() = _foodList

    private val _isFoodListVisible = MutableLiveData<Boolean>()
    val isFoodListVisible: LiveData<Boolean>
        get() = _isFoodListVisible

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _isErrorVisible = MutableLiveData<Boolean>()
    val isErrorVisible: LiveData<Boolean>
        get() = _isErrorVisible

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    init {
        getAllFoods()
    }

    fun onFavouriteClick(position: Int, isChecked: Boolean) {
        auth.currentUser?.let { outer->
            val food = _foodList.value?.let {
                it[position]
            }
            when(isChecked){
                true -> database.reference.child(outer.uid).child("favourite").child(food!!.id.toString()).setValue(food)
                else -> database.reference.child(outer.uid).child("favourite").child(food!!.id.toString()).removeValue()
            }

        }
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            repository.getAllFoods().collect { outer ->
                when (outer) {
                    is Resource.Success -> {
                        outer.data?.let { it ->
                            _foodList.postValue(it.foods)
                            _isFoodListVisible.postValue(true)
                            _loading.postValue(false)
                            _isErrorVisible.postValue(false)
                        }

                    }
                    is Resource.Error -> {
                        outer.message?.let {
                            _error.postValue(it)
                            _isErrorVisible.postValue(true)
                            _loading.postValue(false)
                            _isFoodListVisible.postValue(false)
                        }
                    }
                    is Resource.Loading -> {
                        _loading.postValue(true)
                        _isErrorVisible.postValue(false)
                        _isFoodListVisible.postValue(false)
                    }
                }
            }
        }
    }
}