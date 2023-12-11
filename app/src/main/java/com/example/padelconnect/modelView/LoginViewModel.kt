package com.example.padelconnect.modelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.padelconnect.model.firebase.DatabaseConnection
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth = DatabaseConnection.getAuthInstance()
    private val loginResultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getLoginResult(): LiveData<Boolean> = loginResultLiveData

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                loginResultLiveData.value = task.isSuccessful
            }
    }
}