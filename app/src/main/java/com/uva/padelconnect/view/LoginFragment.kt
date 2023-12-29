package com.uva.padelconnect.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uva.padelconnect.R
import com.uva.padelconnect.databinding.FragmentLoginBinding
import com.uva.padelconnect.modelView.viewmodel.LoginViewModel
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var usersSessionViewModel: UsersSessionViewModel
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
        usersSessionViewModel = ViewModelProvider(this).get(UsersSessionViewModel::class.java)

        // Manejar clic en el botón de inicio de sesión
        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.login(username, password)
            viewModel.getLoginResult()
                .observe(viewLifecycleOwner, Observer { loginResult: Boolean ->
                    if (loginResult) {
                        usersSessionViewModel.obtenerDatosUsuario(username)
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
