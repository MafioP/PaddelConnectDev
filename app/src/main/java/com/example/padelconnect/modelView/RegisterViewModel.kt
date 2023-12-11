package com.example.padelconnect.modelView

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.padelconnect.model.entities.User
import com.example.padelconnect.model.firebase.DatabaseConnection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class RegisterViewModel {
    private val firebaseAuth: FirebaseAuth = DatabaseConnection.getAuthInstance()
    private val usersReference: DatabaseReference = DatabaseConnection.getUsersReference()
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
        country: String,
        imageProfile:ImageView
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // El usuario se registró exitosamente
                    val currentUser = firebaseAuth.currentUser
                    val userId = currentUser?.uid

                    // Crear un objeto con los datos del usuario
                    val user = userId?.let { User(it, name, lastName, username, email, city, country,imageProfile) }

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