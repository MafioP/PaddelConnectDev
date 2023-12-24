package com.example.padelconnect.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.padelconnect.R
import com.example.padelconnect.databinding.FragmentHomeBinding
import com.example.padelconnect.databinding.FragmentLoginBinding
import com.example.padelconnect.modelView.viewmodel.LoginViewModel
import com.example.padelconnect.modelView.viewmodel.ProfileViewModel

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentLoginBinding? =null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view: View = binding.root

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Manejar clic en el botón de inicio de sesión
        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.login(username, password)
            viewModel.getLoginResult()
                .observe(viewLifecycleOwner, Observer { loginResult: Boolean ->
                    if (loginResult) {
                        profileViewModel.obtenerDatosUsuario(username)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
                            .setMessage("Usuario o contraseña incorrectos")
                            .setPositiveButton("Aceptar", null)
                            .show()
                    }
                })
        }

        // Manejar clic en el texto de registro
        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return view
    }
}
