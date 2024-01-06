package com.uva.padelconnect.modelView.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uva.padelconnect.model.entities.Ranking
import com.uva.padelconnect.model.entities.RankingWithUserDetails
import com.uva.padelconnect.modelView.repositories.RankingRepository
import com.uva.padelconnect.modelView.repositories.UserRepository
import androidx.lifecycle.ViewModel

class RankingViewModel : ViewModel() {
    private var rankingRepository: RankingRepository = RankingRepository()
    private var userRepository: UserRepository = UserRepository()

    // Define la propiedad LiveData para almacenar la lista de RankingsWithUserDetails.
    private val _top10Ranking = MutableLiveData<List<RankingWithUserDetails>>()
    val top10Ranking: LiveData<List<RankingWithUserDetails>> get() = _top10Ranking

    // Método para obtener la lista de los 10 mejores usuarios y cargar sus datos completos.
    fun getTop10RankingWithUserDetails() {
        rankingRepository.getTop10Ranking { rankingList ->
            val top10WithUserDetails = mutableListOf<RankingWithUserDetails>()

            rankingList.forEach { ranking ->
                userRepository.obtenerUsuarioById(ranking.userId) { user ->
                    if (user != null) {
                        // Combina el objeto Ranking con los datos del usuario
                        val rankingWithUser = RankingWithUserDetails(user, ranking)
                        top10WithUserDetails.add(rankingWithUser)

                        // Si ya hemos obtenido todos los detalles de los usuarios, actualiza la LiveData
                        if (top10WithUserDetails.size == rankingList.size) {
                            _top10Ranking.postValue(top10WithUserDetails)
                        }
                    }
                }
            }
        }
    }
}