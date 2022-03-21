package com.uguraltintas.sogumadanye.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.uguraltintas.sogumadanye.model.FoodFromCart
import com.uguraltintas.sogumadanye.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>>
        get() = _orderList

    private val tempOrderList: MutableList<Order> = mutableListOf()

    private val _isAnonymous = MutableLiveData<Boolean>()
    val isAnonymous: LiveData<Boolean>
        get() = _isAnonymous

    init {
        fetchData()
    }

    fun isUserAnonymous() {
        auth.currentUser?.let {
            _isAnonymous.value = it.isAnonymous
        }
    }

    private fun fetchData() {
        auth.currentUser?.let { outer ->
            database.reference.child(outer.uid).child("orders")
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        Log.e("data1", snapshot.value.toString())
                        val status = snapshot.child("status").getValue(Int::class.java)
                        val timestamp = snapshot.child("time").getValue(Long::class.java)
                        var tempCost = 0
                        val foodList: MutableList<FoodFromCart> = mutableListOf()

                        snapshot.child("foods").children.forEach {
                            val food = it.getValue(FoodFromCart::class.java)
                            tempCost += food!!.count * food.price
                            foodList.add(food)
                        }
                        val imageName = foodList[0].imageName

                        if (status != null && timestamp != null) {
                            val order = Order(
                                imageName,
                                snapshot.key!!,
                                status,
                                foodList.toList(),
                                timestamp,
                                tempCost
                            )

                            tempOrderList.add(order)
                            _orderList.value = tempOrderList.sortedBy { it.timestamp }.reversed()
                            foodList.clear()
                        }
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                        val status = snapshot.child("status").getValue(Int::class.java)
                        for ((index, item) in tempOrderList.withIndex()) {
                            if (item.id == snapshot.key!!) {
                                val new = tempOrderList[index].copy(status = status!!)
                                tempOrderList[index] = new
                            }
                        }
                        _orderList.value = tempOrderList.sortedBy { it.timestamp }.reversed()

                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {

                    }

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                    override fun onCancelled(error: DatabaseError) {}

                })
        }
    }
}