package com.uva.padelconnect.modelView.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.model.entities.Tournament
import com.uva.padelconnect.modelView.repositories.MatchRepository
import com.uva.padelconnect.modelView.repositories.TournamentRepository

class TournamentDetailsViewModel(

    private val tournamentRepository: TournamentRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    // LiveData para el torneo actual
    private val _tournamentLiveData = MutableLiveData<Tournament>()
    val tournamentLiveData: LiveData<Tournament>
        get() = _tournamentLiveData

    // LiveData para los partidos del torneo
    private val _matchesLiveData = MutableLiveData<List<Match>>()
    val matchesLiveData: LiveData<List<Match>>
        get() = _matchesLiveData

    // LiveData para el número total de personas apuntadas al torneo
    private val _numberOfParticipantsLiveData = MutableLiveData<Int>()
    val numberOfParticipantsLiveData: LiveData<Int>
        get() = _numberOfParticipantsLiveData

    // Método para obtener un torneo por su ID como LiveData
    fun getTournamentById(tournamentId: String) {
        tournamentRepository.getTournamentById(tournamentId).observeForever { tournament ->
            _tournamentLiveData.value = tournament
        }
    }

    // Método para agregar un match a un torneo existente
    fun addMatchToTournament(tournamentId: String, match: Match) {
        tournamentRepository.addMatchToTournament(tournamentId, match)
    }

    // Métodos del MatchRepository para crear un partido y asignar usuario a un partido
    fun createMatch(match: Match, onComplete: (Boolean) -> Unit) {
        matchRepository.createMatch(match, onComplete)
    }

    fun assignUserToMatch(matchId: String, userId: String, pos: Int) {
        matchRepository.asignUserToMatch(matchId, userId, pos)
    }

    // Método para obtener el número total de personas apuntadas a un torneo
    fun getNumberOfParticipantsForTournament(tournamentId: String) {
        tournamentRepository.getNumberOfParticipantsForTournament(tournamentId) { numberOfParticipants ->
            _numberOfParticipantsLiveData.value = numberOfParticipants
        }
    }
}