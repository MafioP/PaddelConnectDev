package com.example.padelconnect.model.entities

import android.widget.ImageView

class User(
    val userId: String = "",
    val name: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val city: String = "",
    val country: String = "",
    val password:String = "",
    val imageView: ImageView? = null
){
}