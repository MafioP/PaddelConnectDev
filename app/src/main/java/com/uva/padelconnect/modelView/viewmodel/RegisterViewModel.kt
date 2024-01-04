package com.uva.padelconnect.modelView.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.User
import com.uva.padelconnect.model.firebase.DatabaseConnection
import com.uva.padelconnect.model.firebase.DatabaseConnection.getImageStorageReference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.uva.padelconnect.modelView.repositories.UserRepository

class RegisterViewModel: ViewModel(){
    private val registerResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var userRepository:UserRepository = UserRepository()

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
        userRepository.registrarUsuario(email, password, name, lastName, username, city, country, imageProfileUri) { success ->
            registerResultLiveData.value = success
        }
    }
}