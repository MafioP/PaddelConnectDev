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
import com.example.padelconnect.modelView.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Referenciar los elementos de la vista (EditText, Button, TextView)
        val editTextUsername: EditText = view.findViewById(R.id.editTextUsername)
        val editTextPassword: EditText = view.findViewById(R.id.editTextPassword)
        val buttonLogin: Button = view.findViewById(R.id.buttonLogin)
        val textViewRegister: TextView = view.findViewById(R.id.textViewRegister)

        // Manejar clic en el botón de inicio de sesión
        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            viewModel.login(username, password)
            viewModel.getLoginResult().observe(viewLifecycleOwner, Observer { loginResult:Boolean ->
                if (loginResult) {
                    findNavController().navigate(R.id.action_loginFragment_to_matchesListFragment)
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
        textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }
}
