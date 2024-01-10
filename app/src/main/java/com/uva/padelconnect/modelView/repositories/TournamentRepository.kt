package com.uva.padelconnect.modelView.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.model.entities.Tournament
import com.uva.padelconnect.model.entities.User
import com.uva.padelconnect.model.firebase.DatabaseConnection.getTournamentReference
import com.uva.padelconnect.model.firebase.FakeFirebaseService
import java.util.Date

class TournamentRepository {
    private val database: DatabaseReference = getTournamentReference()
    // Variable de instancia para llevar un registro del número total de participantes
    private var numberOfParticipants = 0
    private val fakeFirebaseService = FakeFirebaseService()

    // Método para agregar un torneo
    fun addTournament(
        public: Boolean,
        name: String,
        date: Date?,
        place: String,
        doubles: Boolean,
        codigoUnico: String,
        matches: MutableList<Match>
    ) {
        val tournamentId = database.push().key
        tournamentId?.let {
            val tournament = Tournament(
                idTorneo = it,
                public = public,
                name = name,
                date = date,
                place = place,
                doubles = doubles,
                codigoUnico = codigoUnico,
                matches = matches
            )
            database.child(it).setValue(tournament)
        }
    }

    // Método para obtener todos los torneos como LiveData
    fun getAllTournaments2(): LiveData<List<Tournament>> {
        val tournamentsLiveData = MutableLiveData<List<Tournament>>()
        val query = database.orderByChild("date")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tournaments = mutableListOf<Tournament>()
                for (tournamentSnapshot in dataSnapshot.children) {
                    val tournament = tournamentSnapshot.getValue(Tournament::class.java)
                    tournament?.let { tournaments.add(it) }
                }
                tournamentsLiveData.value = tournaments
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores aquí
            }
        })
        return tournamentsLiveData
    }

    fun getAllTournaments(): LiveData<List<Tournament>> {
        val tournamentsLiveData = MutableLiveData<List<Tournament>>()
        val fakeTournaments = fakeFirebaseService.getFakeTournamentsData()

        // Establece el valor de LiveData con la lista de torneos ficticios
        tournamentsLiveData.value = fakeTournaments

        return tournamentsLiveData
    }

    // Método para obtener un torneo por su ID como LiveData
    fun getTournamentById(tournamentId: String): LiveData<Tournament?> {
        val tournamentLiveData = MutableLiveData<Tournament?>()
        val tournamentRef = database.child(tournamentId)
        tournamentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tournament = dataSnapshot.getValue(Tournament::class.java)
                tournamentLiveData.value = tournament
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores aquí
            }
        })
        return tournamentLiveData
    }

    // Método para actualizar un torneo
    fun updateTournament(tournamentId: String, tournament: Tournament) {
        database.child(tournamentId).setValue(tournament)
    }

    // Método para eliminar un torneo
    fun deleteTournament(tournamentId: String) {
        database.child(tournamentId).removeValue()
    }

    // Método para agregar un match a un torneo existente
    fun addMatchToTournament(tournamentId: String, match: Match) {
        val tournamentRef = database.child(tournamentId)
        tournamentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tournament = dataSnapshot.getValue(Tournament::class.java)
                if (tournament != null) {
                    tournament.addMatchToTournament(match)
                    tournamentRef.setValue(tournament)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores aquí
            }
        })
    }

    // Método para obtener los partidos de un torneo por su ID de torneo
    fun getMatchesForTournament(tournamentId: String): LiveData<List<Match>> {
        val matchesLiveData = MutableLiveData<List<Match>>()

        // Consulta la base de datos para obtener los partidos relacionados con el torneo
        val query = database.child("matches").orderByChild("idTorneo").equalTo(tournamentId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val matches = mutableListOf<Match>()
                for (matchSnapshot in dataSnapshot.children) {
                    val match = matchSnapshot.getValue(Match::class.java)
                    match?.let { matches.add(it) }
                }
                matchesLiveData.value = matches
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores aquí
            }
        })

        return matchesLiveData
    }

    // Método para obtener las personas apuntadas a un torneo por su ID de torneo
    fun getParticipantsForTournament(tournamentId: String, callback: (List<String>) -> Unit) {
        val participantsList = mutableListOf<String>()

        // Obtener los matches del torneo
        val matchesQuery = database.child("matches").orderByChild("idTorneo").equalTo(tournamentId)
        matchesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(matchesSnapshot: DataSnapshot) {
                for (matchSnapshot in matchesSnapshot.children) {
                    val match = matchSnapshot.getValue(Match::class.java)
                    match?.let {
                        // Verificar y agregar los usuarios apuntados de cada match
                        if (!it.idUser1.isNullOrEmpty()) {
                            participantsList.add(it.idUser1)
                        }
                        if (!it.idUser2.isNullOrEmpty()) {
                            participantsList.add(it.idUser2)
                        }
                        if (!it.idUser3.isNullOrEmpty()) {
                            participantsList.add(it.idUser3)
                        }
                        if (!it.idUser4.isNullOrEmpty()) {
                            participantsList.add(it.idUser4)
                        }
                    }
                }
                // Llamar al callback con la lista de personas apuntadas
                callback(participantsList.distinct()) // Eliminar duplicados
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores aquí
            }
        })
    }

    // Método para obtener el número total de personas apuntadas a un torneo por su ID de torneo
    fun getNumberOfParticipantsForTournament(tournamentId: String, callback: (Int) -> Unit) {
        numberOfParticipants = 0 // Reiniciar el contador

        // Obtener los matches del torneo
        val matchesQuery = database.child("matches").orderByChild("idTorneo").equalTo(tournamentId)
        matchesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(matchesSnapshot: DataSnapshot) {
                for (matchSnapshot in matchesSnapshot.children) {
                    val match = matchSnapshot.getValue(Match::class.java)
                    match?.let {
                        // Contar usuarios apuntados en cada match
                        if (!it.idUser1.isNullOrEmpty()) {
                            numberOfParticipants++
                        }
                        if (!it.idUser2.isNullOrEmpty()) {
                            numberOfParticipants++
                        }
                        if (!it.idUser3.isNullOrEmpty()) {
                            numberOfParticipants++
                        }
                        if (!it.idUser4.isNullOrEmpty()) {
                            numberOfParticipants++
                        }
                    }
                }
                // Llamar al callback con el número total de personas apuntadas
                callback(numberOfParticipants)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores aquí
            }
        })
    }

}