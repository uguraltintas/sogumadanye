package com.uguraltintas.sogumadanye.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uguraltintas.sogumadanye.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class RegisterStatus{ SUCCESS , FAILED}
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _registerStatus = MutableLiveData<RegisterStatus>()
    val registerStatus : LiveData<RegisterStatus>
    get() = _registerStatus

    fun createUserWithEmailAndPassword(user: User, password: String) {
        auth.createUserWithEmailAndPassword(user.email, password).addOnCompleteListener { outer ->
            if (outer.isSuccessful) {
                val uid = outer.result?.user?.uid
                uid?.let {
                    database.reference.child(it).child("user")
                        .setValue(user)
                    _registerStatus.value = RegisterStatus.SUCCESS
                }
            }else {
                _registerStatus.value = RegisterStatus.FAILED
            }
        }
    }
}