package com.example.padelconnect.modelView.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.padelconnect.model.entities.User
import com.example.padelconnect.model.firebase.DatabaseConnection
import com.example.padelconnect.model.firebase.DatabaseConnection.getImageStorageReference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class RegisterViewModel: ViewModel(){
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
        imageProfileUri: Uri // Cambiado de ImageView a Uri para representar la URI de la imagen
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    val userId = currentUser?.uid

                    val imageRef = getImageStorageReference(userId) // Ruta en Firebase Storage para la imagen de perfil

                    // Subir la imagen a Firebase Storage
                    imageRef.putFile(imageProfileUri)
                        .addOnCompleteListener { uploadTask ->
                            if (uploadTask.isSuccessful) {
                                // Obtener la URL de descarga de la imagen
                                imageRef.downloadUrl.addOnSuccessListener { uri ->
                                    // Crear un objeto con los datos del usuario, incluyendo la URL de la imagen de perfil
                                    val user = userId?.let { User(it, name, lastName, username, email, city, country, "", uri) }

                                    // Guardar los datos del usuario en la base de datos (Firebase Realtime Database)
                                    if (userId != null) {
                                        usersReference.child(userId).setValue(user)
                                            .addOnCompleteListener { databaseTask ->
                                                registerResultLiveData.value = databaseTask.isSuccessful
                                            }
                                    }
                                }
                            } else {
                                registerResultLiveData.value = false
                            }
                        }
                } else {
                    registerResultLiveData.value = false
                }
            }
    }
}