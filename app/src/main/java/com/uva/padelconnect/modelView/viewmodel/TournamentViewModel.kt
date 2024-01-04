package com.uva.padelconnect.modelView.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.Match

class TournamentViewModel: ViewModel() {
    private val createResultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getCreateResult(): LiveData<Boolean> = createResultLiveData

    fun registerTournament(public: Any, name: String, date: Any, city: Any, place: String) {

    }
}