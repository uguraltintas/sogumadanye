package com.uguraltintas.sogumadanye.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.uguraltintas.sogumadanye.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _foodList = MutableLiveData<List<Food>>()
    val foodList: LiveData<List<Food>>
        get() = _foodList

    private val tempFavFoodList: MutableList<Food> = mutableListOf()

    private val _isAnonymous = MutableLiveData<Boolean>()
    val isAnonymous: LiveData<Boolean>
        get() = _isAnonymous

    init {
        getFavouriteFoods()
    }

    fun isUserAnonymous() {
        auth.currentUser?.let {
            _isAnonymous.value = it.isAnonymous
        }
    }

    fun onSwipeFavouriteItem(position: Int) {
        auth.currentUser?.let { outer ->
            val food = _foodList.value?.let {
                it[position]
            }
            database.reference.child(outer.uid).child("favourite")
                    .child(food!!.id.toString()).removeValue()
        }
    }

    private fun getFavouriteFoods() {
        auth.currentUser?.let { outer ->
            database.reference.child(outer.uid).child("favourite")
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        val food = snapshot.getValue(Food::class.java)
                        food?.let {
                            tempFavFoodList.add(it)
                            _foodList.value = tempFavFoodList
                        }

                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        val food = snapshot.getValue(Food::class.java)
                        food?.let {
                            tempFavFoodList.remove(it)
                            _foodList.value = tempFavFoodList
                        }
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                    }
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }
}