package com.example.padelconnect.modelView

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.padelconnect.model.entities.User

class ProfileViewModel: ViewModel() {
    var name: String = ""
    var lastName: String = ""
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var profileImageUri : ImageView? = null

    public fun obtenerDatosDelUsuario(): User {

    }
}