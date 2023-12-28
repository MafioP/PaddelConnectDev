package com.uva.padelconnect.modelView.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.modelView.repositories.UserRepository

class ProfileViewModel: ViewModel() {
    private val userRepository:UserRepository= UserRepository()
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _profileImage = MutableLiveData<Uri>()
    val profileImage: LiveData<Uri> = _profileImage

    public fun obtenerDatosUsuario(username:String ) {
        userRepository.obtenerUsuario(username){user ->
            if(user!=null){
                // Asignar los datos del usuario al ViewModel
                _name.value = user.name
                _lastName.value = user.lastName
                _username.value = user.username
                _email.value = user.email
                _password.value = user.password
                _profileImage.value = user.imageView
            }
        }
    }
}