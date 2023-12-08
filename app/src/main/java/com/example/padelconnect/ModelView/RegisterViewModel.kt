package com.example.padelconnect.ModelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.padelconnect.Model.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterViewModel {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val usersReference: DatabaseReference = firebaseDatabase.reference.child("users")
    private val registerResultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getRegisterResult(): LiveData<Boolean> = registerResultLiveData

    // Método para registrar al usuario
    fun registerUser(
        name: String,
        lastName: String,
        username: String,
        email: String,
        password: String,
        city: String,
        country: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // El usuario se registró exitosamente
                    val currentUser = firebaseAuth.currentUser
                    val userId = currentUser?.uid

                    // Crear un objeto con los datos del usuario
                    val user = userId?.let { User(it, name, lastName, username, email, city, country) }

                    // Guardar los datos del usuario en la base de datos (Firebase Realtime Database)
                    if (userId != null) {
                        usersReference.child(userId).setValue(user)
                            .addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    registerResultLiveData.value=true
                                } else {
                                    registerResultLiveData.value=true
                                }
                            }
                    }
                } else {
                    registerResultLiveData.value=true
                }
            }
    }
}