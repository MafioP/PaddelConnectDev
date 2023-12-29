package com.uva.padelconnect.modelView.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.modelView.repositories.MatchRepository

class MatchesViewModel: ViewModel() {
    private val _matchesLiveData = MutableLiveData<List<Match>>()
    private var matchRepository: MatchRepository = MatchRepository()
    val matchesLiveData: LiveData<List<Match>> get() = _matchesLiveData

    fun getMatchesByCity(city: String) {
        matchRepository.getMatchesByCity(city) { matches ->
            _matchesLiveData.postValue(matches)
        }
    }

    fun getMatches(){
        matchRepository.getMatches(){ matches ->
            _matchesLiveData.postValue(matches)
        }

    }

}