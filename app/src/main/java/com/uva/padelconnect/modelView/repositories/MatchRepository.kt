package com.uva.padelconnect.modelView.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.model.firebase.DatabaseConnection

class MatchRepository {
    private var matchesAccess : DatabaseReference = DatabaseConnection.getMatchesReference()

    fun getMatchesByCity(city: String, callback: (List<Match>) -> Unit) {
        val query = matchesAccess.orderByChild("city").equalTo(city)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val matchesList = mutableListOf<Match>()

                if (dataSnapshot.exists()) {
                    for (matchSnapshot in dataSnapshot.children) {
                        val match = matchSnapshot.getValue(Match::class.java)
                        match?.let { matchesList.add(it) }
                    }
                }
                callback(matchesList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores de lectura de la base de datos si es necesario
                callback(emptyList()) // Devolver lista vacía en caso de error
            }
        })
    }

    fun getMatches(callback: (List<Match>) -> Unit) {
        val query = matchesAccess.orderByChild("city")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val matchesList = mutableListOf<Match>()

                if (dataSnapshot.exists()) {
                    for (matchSnapshot in dataSnapshot.children) {
                        val match = matchSnapshot.getValue(Match::class.java)
                        match?.let { matchesList.add(it) }
                    }
                }
                callback(matchesList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores de lectura de la base de datos si es necesario
                callback(emptyList()) // Devolver lista vacía en caso de error
            }
        })
    }
    fun createMatch(){

    }
}