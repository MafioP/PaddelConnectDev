package com.uva.padelconnect.model.firebase

import android.net.Uri
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.model.entities.Tournament
import com.uva.padelconnect.model.entities.User
import java.util.Date

class FakeFirebaseService {
    fun getFakeUserData(): List<User> {
        // Crea una lista de usuarios ficticios para pruebas
        val userList = mutableListOf<User>()
        userList.add(User("1", "Jane", "Smith", "janesmith", "janesmith@example.com", "City2", "Country2", "password", Uri.parse("fake_uri_2"), mutableListOf(), 1000))
        userList.add(User("2", "John", "Doe", "johndoe", "johndoe@example.com", "City1", "Country1", "password", Uri.parse("fake_uri_1"), mutableListOf(), 900))
        userList.add(User("3", "Alice", "Johnson", "alicejohnson", "alicejohnson@example.com", "City3", "Country3", "password", Uri.parse("fake_uri_3"), mutableListOf(), 700))
        userList.add(User("4", "Bob", "Brown", "bobbrown", "bobbrown@example.com", "City4", "Country4", "password", Uri.parse("fake_uri_4"), mutableListOf(), 600))
        userList.add(User("5", "Eve", "White", "evewhite", "evewhite@example.com", "City5", "Country5", "password", Uri.parse("fake_uri_5"), mutableListOf(), 500))
        userList.add(User("6", "Michael", "Johnson", "michaeljohnson", "michaeljohnson@example.com", "City6", "Country6", "password", Uri.parse("fake_uri_6"), mutableListOf(), 400))
        userList.add(User("7", "Emily", "Davis", "emilydavis", "emilydavis@example.com", "City7", "Country7", "password", Uri.parse("fake_uri_7"), mutableListOf(), 300))
        userList.add(User("8", "David", "Miller", "davidmiller", "davidmiller@example.com", "City8", "Country8", "password", Uri.parse("fake_uri_8"), mutableListOf(), 200))
        userList.add(User("9", "Olivia", "Wilson", "oliviawilson", "oliviawilson@example.com", "City9", "Country9", "password", Uri.parse("fake_uri_9"), mutableListOf(), 100))
        userList.add(User("10", "William", "Lee", "williamlee", "williamlee@example.com", "City10", "Country10", "password", Uri.parse("fake_uri_10"), mutableListOf(), 50))

        return userList.sortedByDescending { it.puntos }
    }

    fun getFakeTournamentsData(): List<Tournament> {
        // Crea una lista de torneos ficticios para pruebas
        val tournamentList = mutableListOf<Tournament>()

        val currentDate = Date()

        tournamentList.add(Tournament("1", false, "Torneo 1", currentDate, "Lugar 1", true, "codigo1", mutableListOf()))
        tournamentList.add(Tournament("2", true, "Torneo 2", currentDate, "Lugar 2", false, "codigo2", mutableListOf()))
        tournamentList.add(Tournament("3", true, "Torneo 3", currentDate, "Lugar 3", false, "codigo3", mutableListOf()))
        tournamentList.add(Tournament("4", true, "Torneo 4", currentDate, "Lugar 4", false, "codigo4", mutableListOf()))
        tournamentList.add(Tournament("5", true, "Torneo 5", currentDate, "Lugar 5", false, "codigo5", mutableListOf()))
        tournamentList.add(Tournament("6", true, "Torneo 6", currentDate, "Lugar 6", false, "codigo6", mutableListOf()))
        tournamentList.add(Tournament("7", true, "Torneo 7", currentDate, "Lugar 7", false, "codigo7", mutableListOf()))
        tournamentList.add(Tournament("8", true, "Torneo 8", currentDate, "Lugar 8", false, "codigo8", mutableListOf()))
        tournamentList.add(Tournament("9", true, "Torneo 9", currentDate, "Lugar 9", false, "codigo9", mutableListOf()))
        tournamentList.add(Tournament("10", true, "Torneo 10", currentDate, "Lugar 10", false, "codigo10", mutableListOf()))

        return tournamentList
    }

    fun getFakeMatches(): List<Match> {
        val matchList = mutableListOf<Match>()
        matchList.add(Match("match1",false, "Match", Date(300000000),"Valladolid", "1", "2", "3", "4", false, "3-4", "sdfsedgq43"))
        matchList.add(Match("match2", true, "Match", Date(400000000), "Madrid", "3", "6", "7", "8", true, "5-6", "sdfsedgq44"))
        matchList.add(Match("match3", false, "Match", Date(500000000), "Barcelona", "9", "10", "10", "8", false, "9-10", "sdfsedgq45"))
        matchList.add(Match("match4", true, "Match", Date(30236235000), "Seville", "7", "6", "9", "10", true, "13-14", "sdfsedgq46"))

        return matchList
    }

}