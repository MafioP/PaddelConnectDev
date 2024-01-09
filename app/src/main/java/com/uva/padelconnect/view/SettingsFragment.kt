package com.uva.padelconnect.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uva.padelconnect.R
import com.uva.padelconnect.modelView.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        // Inicializar el ViewModel
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        // Enlazar elementos de la interfaz de usuario y configurar observadores
        val darkModeSwitch = rootView.findViewById<Switch>(R.id.switchDarkMode)
        val notificationsSwitch = rootView.findViewById<Switch>(R.id.switchNotifications)

        settingsViewModel.getIsDarkModeEnabled().observe(viewLifecycleOwner) { isEnabled ->
            // Actualizar el estado del interruptor de modo oscuro
            darkModeSwitch.isChecked = isEnabled
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Cambiar el estado del modo oscuro cuando se cambia el interruptor
            settingsViewModel.setDarkModeEnabled(isChecked)
        }

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Redirigir a la pantalla de configuración de notificaciones
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireActivity().packageName)
            startActivity(intent)
        }

        return rootView
    }

}
