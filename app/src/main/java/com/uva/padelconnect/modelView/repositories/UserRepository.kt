package com.uva.padelconnect.modelView.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.uva.padelconnect.model.entities.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.uva.padelconnect.model.entities.Ranking
import com.uva.padelconnect.model.firebase.DatabaseConnection.getAuthInstance
import com.uva.padelconnect.model.firebase.DatabaseConnection.getImageStorageReference
import com.uva.padelconnect.model.firebase.DatabaseConnection.getRankingReference
import com.uva.padelconnect.model.firebase.DatabaseConnection.getUsersReference

class UserRepository {
    private val firebaseAuth: FirebaseAuth = getAuthInstance()
    private var usersAccess: DatabaseReference = getUsersReference()
    private var rankingAccess : DatabaseReference = getRankingReference()
    private lateinit var imageAccess:StorageReference

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
    fun editarUsuario(userId: String, name: String, lastName: String, username: String, password : String, city: String, country: String,perfilUri:Uri,callback: (Boolean) -> Unit){
        usersAccess.child(userId).apply {
            child("name").setValue(name)
            child("lastName").setValue(lastName)
            child("username").setValue(username)
            child("password").setValue(password)
            child("city").setValue(city)
            child("country").setValue(country)
        }
        imageAccess = getImageStorageReference(userId) // Ruta en Firebase Storage para la imagen de perfil

        // Actualizar la imagen en Firebase Storage
        imageAccess.putFile(perfilUri).addOnCompleteListener { uploadTask ->
                if (uploadTask.isSuccessful) {
                    // Obtener la URL de descarga de la imagen actualizada
                    imageAccess.downloadUrl.addOnSuccessListener { uri ->
                        // Actualizar la URL de la imagen en la base de datos
                        usersAccess.child(userId).child("imageView").setValue(uri.toString())
                            .addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    callback(true)
                                } else {
                                   callback(false)
                                }
                            }
                    }
                } else {
                    callback(false)
                }
            }
    }
    fun registrarUsuario(email:String,name:String,lastName:String,username:String,password:String,city:String,country:String,perfilUri: Uri, callback: (Boolean) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    val userId = currentUser?.uid
                    Log.d("LOG", "User logged successfully")
                    if (userId != null) {
                        // Crear un registro de ranking para el nuevo usuario
                        val nuevoRanking = Ranking(userId, 0, 0) // Inicialmente, 0 puntos y 0 puntos anteriores

                        editarUsuario(userId, name, lastName, username, password, city, country,perfilUri) { success ->
                            if (success) {
                                rankingAccess.child(userId).setValue(nuevoRanking)

                                val imageAccess = getImageStorageReference(userId)
                                imageAccess.putFile(perfilUri).addOnCompleteListener { uploadTask ->
                                    if (uploadTask.isSuccessful) {
                                        imageAccess.downloadUrl.addOnSuccessListener { uri ->
                                            usersAccess.child(userId).child("imageView").setValue(uri.toString())
                                                .addOnCompleteListener { databaseTask ->
                                                    if (databaseTask.isSuccessful) {
                                                        callback(true)
                                                    } else {
                                                        callback(false)
                                                    }
                                                }
                                        }
                                    } else {
                                        callback(false)
                                    }
                                }
                            } else {
                                callback(false)
                            }
                        }
                    } else {
                        callback(false)
                    }
                } else {
                    Log.w("LOG", "User not registered")
                    callback(false)
                }
            }
    }
    fun obtenerUsuarioById(userId: String, callback: (User?) -> Unit){
        // Lógica para obtener los datos del usuario desde Firebase
        val query = usersAccess.orderByChild("userId").equalTo(userId)

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