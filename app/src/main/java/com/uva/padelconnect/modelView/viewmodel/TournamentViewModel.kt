package com.uva.padelconnect.modelView.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.model.entities.Tournament
import com.uva.padelconnect.modelView.repositories.TournamentRepository
import java.util.Date

class TournamentViewModel: ViewModel() {
    private val repository: TournamentRepository = TournamentRepository()
    private val createResultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getCreateResult(): LiveData<Boolean> = createResultLiveData

    fun registerTournament(public: Any, name: String, date: Any, city: Any, place: String) {

    }

    // LiveData para la lista de torneos
    private var allTournaments: LiveData<List<Tournament>>? = null

    // LiveData para la lista de partidos de un torneo específico
    private var tournamentMatches: LiveData<List<Match>>? = null

    // Método para obtener todos los torneos
    fun getAllTournaments(): LiveData<List<Tournament>> {
        if (allTournaments == null) {
            allTournaments = repository.getAllTournaments()
        }
        return allTournaments!!
    }

    // Método para obtener los partidos de un torneo específico
    fun getMatchesForTournament(tournamentId: String): LiveData<List<Match>> {
        if (tournamentMatches == null) {
            tournamentMatches = repository.getMatchesForTournament(tournamentId)
        }
        return tournamentMatches!!
    }

    // Método para agregar un torneo
    fun createTournament(
        public: Boolean,
        name: String,
        date: Date?,
        place: String,
        doubles: Boolean,
        codigoUnico: String,
        matches: MutableList<Match>
    ) {
        repository.addTournament(public, name, date, place, doubles, codigoUnico, matches)
    }

    // Método para obtener el número total de personas apuntadas a un torneo
    fun getNumberOfParticipantsForTournament(tournamentId: String, callback: (Int) -> Unit) {
        repository.getNumberOfParticipantsForTournament(tournamentId, callback)
    }
}