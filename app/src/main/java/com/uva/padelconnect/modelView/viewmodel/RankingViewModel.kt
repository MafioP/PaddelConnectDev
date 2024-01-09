package com.uva.padelconnect.modelView.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uva.padelconnect.modelView.repositories.UserRepository
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.User

class RankingViewModel : ViewModel() {
    private var userRepository: UserRepository = UserRepository()

    // Define la propiedad LiveData para almacenar la lista de los 10 mejores usuarios por puntuación.
    private val _top10Ranking = MutableLiveData<List<User>>()
    val top10Ranking: LiveData<List<User>> get() = _top10Ranking

    // Método para obtener la lista de los 10 mejores usuarios por puntuación.
    fun getTop10Ranking() {
        userRepository.getTop10Ranking { userList ->
            // Actualiza la LiveData con la lista de los 10 mejores usuarios.
            _top10Ranking.postValue(userList)
        }
    }


}