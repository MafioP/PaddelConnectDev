package com.example.padelconnect.ModelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    // Aquí puedes agregar la lógica relacionada con el inicio de sesión, por ejemplo:
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val loginResultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getLoginResult(): LiveData<Boolean> = loginResultLiveData

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                loginResultLiveData.value = task.isSuccessful
            }
    }
}