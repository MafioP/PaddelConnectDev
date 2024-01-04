package com.uva.padelconnect.view

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uva.padelconnect.databinding.FragmentCreateMatchBinding
import com.uva.padelconnect.modelView.viewmodel.MatchesViewModel
import com.uva.padelconnect.modelView.viewmodel.TournamentViewModel
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel

class CreateMatchFragment: Fragment() {
    private lateinit var viewModelMatches: MatchesViewModel
    private lateinit var viewModelTournament:TournamentViewModel
    private lateinit var usersSession:UsersSessionViewModel
    private var _binding: FragmentCreateMatchBinding? =null
    private val binding get() = _binding!!
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMatchBinding.inflate(inflater, container, false)
        val view:View=binding.root

        viewModelMatches = ViewModelProvider(this).get(MatchesViewModel::class.java)
        viewModelTournament = ViewModelProvider(this).get(TournamentViewModel::class.java)
        usersSession = ViewModelProvider(this).get(UsersSessionViewModel::class.java)

        return view
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Agregar acciones para el botón de copiar enlace
            binding.buttonCopyLink.setOnClickListener {
                val link = binding.editTextLink.toString().trim()
                if (link.isNotEmpty()) {
                    val clipboard =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Link", link)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(
                        requireContext(),
                        "Enlace copiado al portapapeles",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Introduce un enlace primero",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // Agregar acciones para el botón de crear partido/torneo
            binding.buttonCreateMatch.setOnClickListener {
                val selectedMatchPrivacy = binding.spinnerMatch.selectedItem.toString()
                val selectedMatchType = binding.spinnerDoubles.selectedItem.toString()
                val name = binding.editTextMatchName.text.toString()
                val fechaString = binding.editTextDate.text.toString()
                val place = binding.editTextPlace.text.toString()
                val type = binding.spinnerType.selectedItem.toString()
                // Asegúrate de tener al menos tres partes (calle, número, ciudad)
                if (type == "Partido") {
                    usersSession.userId.observe(viewLifecycleOwner){userId->
                        // Llamar al ViewModel para realizar el registro
                        viewModelMatches.registerMatch(selectedMatchPrivacy, name, fechaString, place, selectedMatchType,userId)
                    }
                    viewModelMatches.getCreateResult().observe(viewLifecycleOwner, Observer { createResult: Boolean ->
                            if (createResult) {
                                findNavController().navigate(com.uva.padelconnect.R.id.action_createMatchFragment_to_matchListFragment)
                            } else {
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Error")
                                    .setMessage("Ha habido un problema al crear el partido\nPor favor, intentelo de nuevo")
                                    .setPositiveButton("Aceptar", null)
                                    .show()
                            }
                        })
                } else {
                    viewModelTournament.registerTournament(selectedMatchPrivacy, name, fechaString, place, selectedMatchType)
                    viewModelTournament.getCreateResult().observe(viewLifecycleOwner, Observer { createResult: Boolean ->
                            if (createResult) {
                                findNavController().navigate(com.uva.padelconnect.R.id.action_createMatchFragment_to_TournamentFragment)
                            } else {
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Error")
                                    .setMessage("Ha habido un problema al crear el torneo\nPor favor, intentelo de nuevo")
                                    .setPositiveButton("Aceptar", null)
                                    .show()
                            }
                        })
                }
            }
        }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}