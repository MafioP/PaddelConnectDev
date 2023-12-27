package com.uva.padelconnect.modelView.repositories

import com.uva.padelconnect.model.entities.User
import com.uva.padelconnect.model.firebase.DatabaseConnection
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class UserRepository {
    private var usersAccess :DatabaseReference = DatabaseConnection.getUsersReference()

    fun obtenerUsuario(username: String, callback: (User?) -> Unit){
        // Lógica para obtener los datos del usuario desde Firebase
        val query = usersAccess.orderByChild("username").equalTo(username)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Lógica para manejar los datos obtenidos del usuario
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        // Construir un objeto User utilizando los datos del DataSnapshot
                        val user = userSnapshot.getValue(User::class.java)
                        callback(user)
                        return
                    }
                }
                // Si no se encuentra ningún usuario con ese email
                callback(null) // Llamar al callback con null si no se encuentra el usuario
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores de lectura de la base de datos si es necesario
                callback(null) // Llamar al callback con null en caso de error
            }
        })
    }
}