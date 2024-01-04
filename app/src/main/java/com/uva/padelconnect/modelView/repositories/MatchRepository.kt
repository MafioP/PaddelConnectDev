package com.uva.padelconnect.modelView.repositories

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.model.firebase.DatabaseConnection
import java.util.Date

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
    fun createMatch(match: Match, onComplete: (Boolean) -> Unit) {
        val matchId = matchesAccess.child("matches").push().key // Generar ID para el partido
        matchId?.let { id ->
            val matchRef = matchesAccess.child("matches").child(id)
            match.idMatch = id // Asignar el ID generado al partido

            matchRef.setValue(match)
                .addOnCompleteListener { task ->
                    onComplete(task.isSuccessful)
                }
        } ?: run {
            onComplete(false)
        }
    }

    fun asignUserToMatch(matchId: String, userId: String, pos: Int) {
        matchesAccess.child(matchId).apply {
            when (pos) {
                2 -> child("idUser2").setValue(userId)
                3 -> child("idUser3").setValue(userId)
                4 -> child("idUser4").setValue(userId)
            }
        }
    }

    fun updateResult(matchId: String, result: String) {
        matchesAccess.child(matchId).apply {
                child("resultado").setValue(result)
        }
    }
}