package com.example.padelconnect.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.padelconnect.R
import com.example.padelconnect.modelView.RegisterViewModel

class RegisterFragment: Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)


        // Referenciar los elementos de la vista (EditText, Button, ImageView)
        val editTextName: EditText = view.findViewById(R.id.editTextName)
        val editTextLastName: EditText = view.findViewById(R.id.editTextLastName)
        val editTextUsername: EditText = view.findViewById(R.id.editTextUsername)
        val editTextEmail: EditText = view.findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = view.findViewById(R.id.editTextPassword)
        val imageViewProfile: ImageView = ImageView(context) // Donde "context" es el contexto actual, como el contexto de la actividad o el fragmento
        // Asignar la imagen predeterminada desde los recursos
        imageViewProfile.setImageResource(R.drawable.png_transparent_computer_icons_profile_s_free_angle_sphere_profile_cliparts_free)
        val editTextCity: EditText = view.findViewById(R.id.editTextCity)
        val editTextCountry: EditText = view.findViewById(R.id.editTextCountry)
        val buttonRegister: Button = view.findViewById(R.id.buttonRegister)


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
            viewModel.registerUser(name, lastName, username, email, password, city, country,imageViewProfile)
            viewModel.getRegisterResult().observe(viewLifecycleOwner, Observer { registerResult:Boolean ->
            if (registerResult) {
                findNavController().navigate(R.id.action_registerFragment_to_matchesListFragment)
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Ha habido un problema al crear el usuario\nPor favor, intentelo de nuevo")
                    .setPositiveButton("Aceptar", null)
                    .show()
            }
        })
        }
        return view
    }
}

