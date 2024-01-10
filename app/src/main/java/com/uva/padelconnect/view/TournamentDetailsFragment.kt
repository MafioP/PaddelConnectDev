package com.uva.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uva.padelconnect.R
import com.uva.padelconnect.model.entities.Tournament
import com.uva.padelconnect.modelView.viewmodel.TournamentDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.lifecycle.Observer
import com.uva.padelconnect.databinding.FragmentTournamentDetailsBinding

class TournamentDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTournamentDetailsBinding
    private lateinit var viewModel: TournamentDetailsViewModel
    private lateinit var tournamentId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tournament_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtiene el tournamentId de los argumentos
        tournamentId = arguments?.getString("tournamentId") ?: ""

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(this).get(TournamentDetailsViewModel::class.java)

        // Observa los cambios en el LiveData del torneo
        viewModel.tournamentLiveData.observe(viewLifecycleOwner, { tournament ->
            updateUI(tournament)
        })

        // Obtiene el torneo por su ID
        viewModel.getTournamentById(tournamentId)
    }

    private fun updateUI(tournament: Tournament) {
        // Actualiza las vistas con los datos del torneo
        binding.tournamentNameTextView.text = tournament.name
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(tournament.date ?: Date())
        binding.tournamentDateTextView.text = "Fecha: $formattedDate"
        binding.locationTextView.text = "Ubicación: ${tournament.place}"
        binding.privacyTextView.text =
            if (tournament.public) "Privacidad: Público" else "Privacidad: Privado"
        binding.matchmakingModeTextView.text =
            if (tournament.doubles) "Modo de emparejamiento: Parejas" else "Modo de emparejamiento: Individual"

        // Obtén el número de participantes y actualiza la vista correspondiente
        viewModel.getNumberOfParticipantsForTournament(tournamentId)
        viewModel.numberOfParticipantsLiveData.observe(viewLifecycleOwner, { numberOfParticipants ->
            binding.registeredParticipantsTextView.text = "Personas apuntadas: $numberOfParticipants"
        })

        // Gestiona la visibilidad del campo de entrada de usuario de pareja y del botón
        if (tournament.doubles) {
            binding.partnerUsernameEditText.visibility = View.VISIBLE
        } else {
            binding.partnerUsernameEditText.visibility = View.GONE
        }

        // Gestiona la visibilidad del botón "Unirse al Torneo" según la privacidad del torneo
        if (tournament.public) {
            binding.joinTournamentButton.text = "Unirse al Torneo"
            // Aquí puedes agregar la lógica para unirse al torneo si es público
        } else {
            binding.joinTournamentButton.text = "Solicitar acceso"
            // Aquí puedes agregar la lógica para solicitar acceso si es privado
        }
    }

}