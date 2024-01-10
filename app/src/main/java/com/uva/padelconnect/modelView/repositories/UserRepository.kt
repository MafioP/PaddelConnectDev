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
import com.uva.padelconnect.model.firebase.DatabaseConnection.getAuthInstance
import com.uva.padelconnect.model.firebase.DatabaseConnection.getImageStorageReference
import com.uva.padelconnect.model.firebase.DatabaseConnection.getUsersReference
import com.uva.padelconnect.model.firebase.FakeFirebaseService

class UserRepository {
    private val firebaseAuth: FirebaseAuth = getAuthInstance()
    private var usersAccess: DatabaseReference = getUsersReference()
    private lateinit var imageAccess:StorageReference

    private val fakeFirebaseService = FakeFirebaseService()

    fun obtenerUsuario(email: String, callback: (User?) -> Unit){
        // Lógica para obtener los datos del usuario desde Firebase
        val query = usersAccess.orderByChild("email").equalTo(email)

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
                        editarUsuario(userId, name, lastName, username, password, city, country,perfilUri) { success ->
                            if (success) {
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

    fun setPuntos(userId: String, nuevosPuntos: Int, callback: (Boolean) -> Unit) {
        usersAccess.child(userId).child("puntos").setValue(nuevosPuntos)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    fun getTop10Ranking2(callback: (List<User>) -> Unit) {
        usersAccess.orderByChild("puntos")
            .limitToLast(10)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userList = mutableListOf<User>()

                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        user?.let { userList.add(it) }
                    }

                    // Ordenar la lista en orden descendente de puntos
                    userList.sortByDescending { it.puntos }

                    callback(userList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejo de errores si es necesario
                    callback(emptyList())
                }
            })
    }

    fun getTop10Ranking(callback: (List<User>) -> Unit) {
        val userList = fakeFirebaseService.getFakeUserData()

        callback(userList)
    }

}