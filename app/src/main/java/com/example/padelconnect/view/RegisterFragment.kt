package com.example.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.padelconnect.modelView.RegisterViewModel
import com.example.padelconnect.R

class RegisterFragment: Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Inicializar ViewModel
        //viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // Referenciar los elementos de la vista (EditText, Button, ImageView)
        val editTextName: EditText = view.findViewById(R.id.editTextName)
        val editTextLastName: EditText = view.findViewById(R.id.editTextLastName)
        val editTextUsername: EditText = view.findViewById(R.id.editTextUsername)
        val editTextEmail: EditText = view.findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = view.findViewById(R.id.editTextPassword)
        val imageViewProfile: ImageView = view.findViewById(R.id.imageViewProfile)
        val buttonSelectPhoto: Button = view.findViewById(R.id.buttonSelectPhoto)
        val editTextCity: EditText = view.findViewById(R.id.editTextCity)
        val editTextCountry: EditText = view.findViewById(R.id.editTextCountry)
        val buttonRegister: Button = view.findViewById(R.id.buttonRegister)

        // Implementar lógica de selección de foto de perfil si es necesario

        // Manejar clic en el botón de registro
        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString()
            val lastName = editTextLastName.text.toString()
            val username = editTextUsername.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val city = editTextCity.text.toString()
            val country = editTextCountry.text.toString()

            // Llamar al ViewModel para realizar el registro
            //viewModel.registerUser(name, lastName, username, email, password, city, country)
        }

        return view
    }
}