package com.uguraltintas.sogumadanye.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.uguraltintas.sogumadanye.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _cartCount = MutableLiveData<Int>()
    val cartCount: LiveData<Int>
        get() = _cartCount

    fun getCartCount() {
        auth.currentUser?.let { outer ->
            database.reference.child(outer.uid).child("user")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val value = snapshot.getValue(User::class.java)
                        value?.let {
                            _cartCount.value = it.cartCount
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }
}