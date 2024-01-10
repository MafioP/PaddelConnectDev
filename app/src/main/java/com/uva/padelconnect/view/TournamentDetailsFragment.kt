package com.uva.padelconnect.view

import android.os.Bundle
import androidx.fragment.app.Fragment

class TournamentDetailsFragment : Fragment() {
    companion object {
        fun newInstance(tournamentId: String): TournamentDetailsFragment {
            val fragment = TournamentDetailsFragment()
            val args = Bundle()
            args.putString("tournamentId", tournamentId)
            fragment.arguments = args
            return fragment
        }
    }

}