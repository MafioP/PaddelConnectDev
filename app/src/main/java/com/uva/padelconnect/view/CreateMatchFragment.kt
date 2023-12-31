package com.uva.padelconnect.view

import android.R
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uva.padelconnect.databinding.FragmentCreateMatchBinding

class CreateMatchFragment: Fragment() {
    private var _binding: FragmentCreateMatchBinding? =null

    private val binding get() = _binding!!
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Aquí puedes trabajar con los elementos de tu diseño utilizando binding

            // Por ejemplo, configurar el Spinner para el tipo de partido
            val typeOptions = requireContext().resources.getStringArray(R.array.type_options)
            val typeAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, typeOptions)
            typeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerType.adapter = typeAdapter

            // Configurar el Spinner para la privacidad del partido
            val matchOptions = resources.getStringArray(R.array.match_options)
            val matchAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, matchOptions)
            matchAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerMatch.adapter = matchAdapter

            // Agregar acciones para el botón de copiar enlace
            binding.buttonCopyLink.setOnClickListener {
                    val link = binding.editTextLink.toString().trim()
                    if (link.isNotEmpty()) {
                        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Link", link)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(this, "Enlace copiado al portapapeles", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Introduce un enlace primero", Toast.LENGTH_SHORT).show()
                    }
            }

            // Agregar acciones para el botón de crear partido/torneo
            binding.buttonCreateMatch.setOnClickListener {
                val name = binding.editTextName.text.toString()
                val city = binding.editTextCity.text.toString()
                val place = binding.editTextCountry.text.toString()

                // Llamar al ViewModel para realizar el registro
                viewModel.registerUser(public,name, lastName, username, email, password, city, country,imageViewProfile)
                viewModel.getRegisterResult().observe(viewLifecycleOwner, Observer { registerResult:Boolean ->
                    if (registerResult) {
                        findNavController().navigate(com.uva.padelconnect.R.id.action_registerFragment_to_homeFragment)
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

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}