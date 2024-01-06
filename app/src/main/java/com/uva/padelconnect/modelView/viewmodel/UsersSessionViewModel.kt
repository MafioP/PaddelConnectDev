package com.uva.padelconnect.modelView.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.User
import com.uva.padelconnect.modelView.repositories.MatchRepository
import com.uva.padelconnect.modelView.repositories.UserRepository

class UsersSessionViewModel: ViewModel() {
    private val userRepository:UserRepository= UserRepository()
    private var matchRepository: MatchRepository = MatchRepository()
    private val _userId = MutableLiveData<String>()
    val userId:LiveData<String> = _userId
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
    private val _likedMatches = MutableLiveData<List<String>>()
    val likedMatches: LiveData<List<String>> = _likedMatches

    fun obtenerDatosUsuario(username:String ) {
        userRepository.obtenerUsuario(username){user ->
            if(user!=null){
                _userId.value=user.userId
                // Asignar los datos del usuario al ViewModel
                _name.value = user.name
                _lastName.value = user.lastName
                _username.value = user.username
                _email.value = user.email
                _password.value = user.password
                _profileImage.value = user.imageView
                _city.value=user.city
                _country.value=user.country
                _likedMatches.value=user.likedMatches
            }
        }
    }

    fun actualizarDatos(name: String, lastName: String, username: String,password:String, city: String, country: String, imageViewUri: Uri){
        val userid: String? =_userId.value
        _name.value = name
        _lastName.value = lastName
        _username.value = username
        _password.value = password
        _profileImage.value = imageViewUri
        _city.value=city
        _country.value=country
        if (userid != null) {
            userRepository.editarUsuario(userid,name,lastName,username,password,city,country,imageViewUri){ success ->
                if (success) {
                    // La edición del usuario fue exitosa
                } else {
                    // Ocurrió un error al editar el usuario
                }
            }
        }
    }

    fun likeMatch(matchId: String) {
        val currentLikedMatches = _likedMatches.value?.toMutableList() ?: mutableListOf()
        if (!currentLikedMatches.contains(matchId)) {
            currentLikedMatches.add(matchId)
            _likedMatches.value = currentLikedMatches
        }
    }

    fun unlikeMatch(matchId: String) {
        val currentLikedMatches = _likedMatches.value.orEmpty().toMutableList() // Obtener la lista actual de IDs
        // Eliminar el matchId de la lista
        currentLikedMatches.remove(matchId)
        _likedMatches.value = currentLikedMatches
        matchRepository.unlikeMatch(matchId)
    }
}