package com.uva.padelconnect.model.entities

import android.net.Uri

data class User(
    val userId: String = "",
    val name: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val city: String = "",
    val country: String = "",
    val password:String = "",
    val imageView: Uri
){
}