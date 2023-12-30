package com.uva.padelconnect.modelView.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.User
import com.uva.padelconnect.modelView.repositories.UserRepository

class UsersSessionViewModel: ViewModel() {
    private val userRepository:UserRepository= UserRepository()
    private lateinit var userId:String
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
    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city
    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country

     fun obtenerDatosUsuario(username:String ) {
        userRepository.obtenerUsuario(username){user ->
            if(user!=null){
                userId=user.userId
                // Asignar los datos del usuario al ViewModel
                _name.value = user.name
                _lastName.value = user.lastName
                _username.value = user.username
                _email.value = user.email
                _password.value = user.password
                _profileImage.value = user.imageView
                _city.value=user.city
                _country.value=user.country
            }
        }
    }

    fun actualizarDatos(name: String, lastName: String, username: String,password:String, city: String, country: String, imageViewUri: Uri){
        _name.value = name
        _lastName.value = lastName
        _username.value = username
        _password.value = password
        _profileImage.value = imageViewUri
        _city.value=city
        _country.value=country
        userRepository.editarUsuario(userId,name,lastName,username,password,city,country,imageViewUri)
    }
}