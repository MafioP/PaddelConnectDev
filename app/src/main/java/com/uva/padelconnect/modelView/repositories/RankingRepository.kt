package com.uva.padelconnect.modelView.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.uva.padelconnect.model.entities.Ranking
import com.uva.padelconnect.model.firebase.DatabaseConnection

class RankingRepository {
    private var rankingAccess : DatabaseReference = DatabaseConnection.getRankingReference()

    fun getRankingForUser(userId: String, callback: (Ranking?) -> Unit) {
        val userRankingReference = rankingAccess.child(userId)
        userRankingReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val dataSnapshot = task.result
                val ranking = dataSnapshot?.getValue(Ranking::class.java)
                callback(ranking)
            } else {
                callback(null)
            }
        }
    }

    fun updateRankingForUser(userId: String, newRanking: Ranking) {
        val userRankingReference = rankingAccess.child(userId)
        userRankingReference.setValue(newRanking)
    }

    fun getTop10Ranking(callback: (List<Ranking>) -> Unit) {
        rankingAccess.orderByChild("puntos")
            .limitToLast(10)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val rankingList = mutableListOf<Ranking>()

                    for (userSnapshot in dataSnapshot.children) {
                        val ranking = userSnapshot.getValue(Ranking::class.java)
                        ranking?.let { rankingList.add(it) }
                    }

                    // Ordenar la lista en orden ascendente de puntos
                    rankingList.sortByDescending { it.puntos }

                    callback(rankingList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejo de errores si es necesario
                }
            })
    }


}