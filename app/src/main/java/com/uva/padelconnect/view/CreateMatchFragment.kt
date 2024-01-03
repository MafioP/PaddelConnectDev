package com.uva.padelconnect.view

import android.R
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
import androidx.lifecycle.ViewModelProvider
import com.uva.padelconnect.databinding.FragmentCreateMatchBinding
import com.uva.padelconnect.modelView.viewmodel.MatchesViewModel

class CreateMatchFragment: Fragment() {
    private var _binding: FragmentCreateMatchBinding? =null

    private val binding get() = _binding!!
    private var viewModel: MatchesViewModel? = null
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMatchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MatchesViewModel::class.java]
        return binding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Aquí puedes trabajar con los elementos de tu diseño utilizando binding

            // Por ejemplo, configurar el Spinner para el tipo de partido
            /*
            val typeOptions = requireContext().resources.getStringArray(R.array.type_options)
            val typeAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, typeOptions)
            typeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerType.adapter = typeAdapter

            // Configurar el Spinner para la privacidad del partido
            val matchOptions = resources.getStringArray(R.array.match_options)
            val matchAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, matchOptions)
            matchAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerMatch.adapter = matchAdapter
            */
            // Agregar acciones para el botón de copiar enlace
            binding.buttonCopyLink.setOnClickListener {
                    val link = binding.editTextLink.toString().trim()
                    if (link.isNotEmpty()) {
                        //val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Link", link)
                        //clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Enlace copiado al portapapeles", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Introduce un enlace primero", Toast.LENGTH_SHORT).show()
                    }
            }

            // Agregar acciones para el botón de crear partido/torneo
            binding.buttonCreateMatch.setOnClickListener {
                val name = binding.textViewName.text.toString()
                val place = binding.textViewPlace.text.toString()
                val date = binding.textViewDate.text.toString()
                val matchType = binding.textViewType.text.toString()
                val matchPriv = binding.textViewPrivacity.text.toString()
                val matchDoubles = binding.textViewDoubles.text.toString()

                // Llamar al ViewModel para crear el partido
                viewModel?.createMatch(name, date, place, matchType, matchPriv, matchDoubles)
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}