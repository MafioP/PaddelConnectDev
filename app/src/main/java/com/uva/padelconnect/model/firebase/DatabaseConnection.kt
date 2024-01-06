package com.uva.padelconnect.model.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object DatabaseConnection {

    private var firebaseDatabase: FirebaseDatabase? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseStorage: FirebaseStorage?=null
    fun getDatabaseReference(): FirebaseDatabase {
        firebaseDatabase = firebaseDatabase ?: FirebaseDatabase.getInstance()
        return firebaseDatabase!!
    }
    fun getStorageReference():FirebaseStorage{
        firebaseStorage = firebaseStorage?: FirebaseStorage.getInstance()
        return firebaseStorage!!
    }

    fun getAuthInstance():FirebaseAuth{
        firebaseAuth = firebaseAuth?:FirebaseAuth.getInstance()
        return firebaseAuth!!
    }

    fun getUsersReference(): DatabaseReference {
        getDatabaseReference()
        return firebaseDatabase!!.reference.child("users")
    }

    fun getMatchesReference(): DatabaseReference {
        getDatabaseReference()
        return firebaseDatabase!!.reference.child("matches")
    }

    fun getImageStorageReference(userId: String?): StorageReference {
        getStorageReference()
        return firebaseStorage!!.reference.child("images/$userId/profile.jpg")
    }

    fun getRankingReference(): DatabaseReference {
        getDatabaseReference()
        return firebaseDatabase!!.reference.child("ranking")
    }


}