package com.uva.padelconnect.view

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uva.padelconnect.R
import com.uva.padelconnect.databinding.FragmentRegisterBinding
import com.uva.padelconnect.modelView.viewmodel.RegisterViewModel

class RegisterFragment: Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Imagen por defecto para el perfil
        val imageViewProfile: Uri = Uri.parse("android.resource://com.example.padelconnect/${R.drawable.png_transparent_computer_icons_profile_s_free_angle_sphere_profile_cliparts_free}")

        // Manejar clic en el botón de registro
        binding.buttonRegister.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val username = binding.editTextUsername.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val city = binding.editTextCity.text.toString()
            val country = binding.editTextCountry.text.toString()

            // Llamar al ViewModel para realizar el registro
            viewModel.registerUser(name, lastName, username, email, password, city, country,imageViewProfile)
            viewModel.getRegisterResult().observe(viewLifecycleOwner, Observer { registerResult:Boolean ->
                if (registerResult) {
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage("Ha habido un problema al crear el usuario\nPor favor, intentelo de nuevo")
                        .setPositiveButton("Aceptar", null)
                        .show()
                }
            })
        }
    }

}

