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
import com.uva.padelconnect.model.firebase.DatabaseConnection.getTournamentReference

class TournamentRepository {
    private val database: DatabaseReference = getTournamentReference()

    // Método para agregar un torneo
    fun addTournament(tournament: Tournament) {
        val tournamentId = database.push().key
        tournamentId?.let {
            database.child(it).setValue(tournament)
        }
    }

    // Método para obtener todos los torneos como LiveData
    fun getAllTournaments(): LiveData<List<Tournament>> {
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

}