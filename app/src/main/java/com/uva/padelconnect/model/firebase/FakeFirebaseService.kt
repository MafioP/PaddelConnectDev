package com.uva.padelconnect.model.firebase

import android.net.Uri
import com.uva.padelconnect.model.entities.User

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

}