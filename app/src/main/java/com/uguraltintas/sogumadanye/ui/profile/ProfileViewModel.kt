package com.uguraltintas.sogumadanye.ui.profile

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
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _isAnonymous = MutableLiveData<Boolean>()
    val isAnonymous: LiveData<Boolean>
        get() = _isAnonymous

    fun isUserAnonymous() {
        auth.currentUser?.let {
            _isAnonymous.value = it.isAnonymous
        }
    }

    fun getUserData() {
        auth.currentUser?.let { outer ->
            database.reference.child(outer.uid).child("user")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val value = snapshot.getValue(User::class.java)
                        value?.let {
                            _user.value = it
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }

    fun signOut(){
        auth.signOut()
    }
}