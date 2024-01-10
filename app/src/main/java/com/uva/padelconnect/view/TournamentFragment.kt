package com.uva.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uva.padelconnect.R
import com.uva.padelconnect.modelView.viewmodel.TournamentViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class TournamentFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tournament, container, false)

        // Obtener una referencia al contenedor de CardViews
        val containerForCardViews = view.findViewById<LinearLayout>(R.id.container_for_cardviews)

        // Obtener la lista de torneos desde el ViewModel
        val tournamentViewModel = ViewModelProvider(requireActivity()).get(TournamentViewModel::class.java)
        tournamentViewModel.getAllTournaments().observe(viewLifecycleOwner) { tournaments ->
            // Llenar dinámicamente los CardViews con información de torneos
            if (tournaments.isEmpty()) {
                // Maneja el caso en el que la lista de torneos esté vacía
                val emptyDataTextView = TextView(requireContext())
                emptyDataTextView.text = "No hay torneos disponibles en estos momentos."
                containerForCardViews?.addView(emptyDataTextView)
            } else {
                // La lista no está vacía, así que crea y configura los CardViews
                // Llenar dinámicamente los CardViews con información de torneos
                tournaments.forEach { tournament ->
                    val cardView = layoutInflater.inflate(R.layout.cardviewtorneos, containerForCardViews, false)
                    val tournamentNameTextView = cardView.findViewById<TextView>(R.id.tournamentNameTextView)
                    val tournamentDateTextView = cardView.findViewById<TextView>(R.id.tournamentDateTextView)
                    val tournamentPrivacyTextView = cardView.findViewById<TextView>(R.id.tournamentPrivacyTextView)
                    val matchmakingModeTextView = cardView.findViewById<TextView>(R.id.matchmakingModeTextView)
                    val arrowImageButton = cardView.findViewById<ImageButton>(R.id.arrowImageButton)

                    tournamentNameTextView.text = tournament.name
                    // Formatear la fecha y hora
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    val formattedDate = dateFormat.format(tournament.date ?: Date())
                    tournamentDateTextView.text = "Fecha y hora: $formattedDate"
                    tournamentPrivacyTextView.text = "Privacidad: ${if (tournament.public) "Público" else "Privado"}"
                    matchmakingModeTextView.text = "Modo de Emparejamiento: ${if (tournament.doubles) "Parejas" else "Individual"}"

                    arrowImageButton.setOnClickListener {
                        // Cuando se hace clic en la flecha, abrir TournamentDetailsFragment y pasar el idTorneo
                        val bundle = Bundle()
                        bundle.putString("tournamentId", tournament.idTorneo)

                        val tournamentDetailsFragment = TournamentDetailsFragment()
                        tournamentDetailsFragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, tournamentDetailsFragment)
                            .addToBackStack(null)
                            .commit()
                    }

                    containerForCardViews.addView(cardView)
                }
            }

        }

        return view
    }
}