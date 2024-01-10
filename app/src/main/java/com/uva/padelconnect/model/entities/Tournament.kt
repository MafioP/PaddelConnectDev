package com.uva.padelconnect.model.entities

import java.util.Date
import com.uva.padelconnect.model.entities.Match

data class Tournament(
    var idTorneo: String = "",
    val public: Boolean = true,
    val name: String = "",
    val date: Date?,
    val place: String = "",
    val doubles: Boolean = false,
    val codigoUnico: String = "",
    val matches: MutableList<Match> = mutableListOf()
    ){
    fun addMatchToTournament(match: Match) {
        matches.add(match)
    }
}
