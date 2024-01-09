package com.uva.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uva.padelconnect.databinding.FragmentSettingsBinding
import com.uva.padelconnect.model.firebase.DatabaseConnection
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel

class SettingsFragment : Fragment() {
    private lateinit var usersSession: UsersSessionViewModel

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersSession = ViewModelProvider(this).get(UsersSessionViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Implementar lógica para el Modo Oscuro según el valor de isChecked
            // Por ejemplo:
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            requireActivity().recreate()
        }

        // Configurar el listener para el switch de Notificaciones
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Desactivar las Notificaciones
            } else {
                // Activar las Notificaciones
            }
        }

        // Configurar el listener para el botón de Cerrar Sesión
        binding.button.setOnClickListener {
            usersSession.logOut()
        }
    }

}
