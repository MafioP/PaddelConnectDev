package com.example.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.padelconnect.R
import com.example.padelconnect.model.entities.User
import com.example.padelconnect.modelView.ProfileViewModel

class ProfileFragment: Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var textViewUserName:TextView
    private lateinit var textViewUserLastName:TextView
    private lateinit var textViewUsername:TextView
    private lateinit var textViewUserEmail:TextView
    private lateinit var textViewUserPassword:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //Crear las variables para ponerlas el texto
        textViewUserName = view.findViewById(/* id = */ R.id.textViewUserRealName)
        textViewUserLastName = view.findViewById(R.id.textViewUserLastName)
        textViewUsername = view.findViewById(R.id.textViewUsername)
        textViewUserEmail = view.findViewById(R.id.textViewUserEmail)
        textViewUserPassword = view.findViewById(R.id.textViewPassword)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Supongamos que tienes una clase ViewModel llamada viewModel con los datos del usuario
        val user: User = obtenerDatosDelUsuario() // Esta función debe obtener los datos del usuario después del inicio de sesión

        // Asignar los datos del usuario al ViewModel
        viewModel.name = user.name
        viewModel.lastName = user.lastName
        viewModel.username = user.username
        viewModel.email = user.email
        viewModel.password = user.password
        viewModel.profileImageUri = user.imageView

        // Aquí estableces los datos en los TextView correspondientes
        textViewUserName.text = viewModel.name
        textViewUserLastName.text = viewModel.lastName
        textViewUsername.text = viewModel.username
        textViewUserEmail.text = viewModel.email
        textViewUserPassword.text = viewModel.password

        // Cargar la imagen del usuario desde la referencia en la base de datos
        // Supongamos que tienes la URL de la imagen del usuario almacenada en viewModel.profileImageUri
        // Aquí debes usar alguna librería como Glide o Picasso para cargar la imagen en el ImageView
        // Glide.with(this).load(viewModel.profileImageUri).into(imageViewUser)
    }
}