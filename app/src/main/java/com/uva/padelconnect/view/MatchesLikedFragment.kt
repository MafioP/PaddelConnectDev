package com.uva.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uva.padelconnect.R
import com.uva.padelconnect.modelView.viewmodel.MatchesViewModel
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel

class MatchesLikedFragment: Fragment() {
    private val matchesViewModel: MatchesViewModel by viewModels()
    private val usersViewModel: UsersSessionViewModel by viewModels()
    private var columnCount = 1
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_matches_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMatches)
        recyclerView.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        // Inicializar el adaptador (puedes pasar datos vacíos inicialmente)
        val adapter = MatchesAdapter(matchesViewModel)
        // Establecer el adaptador en el RecyclerView
        recyclerView.adapter = adapter

        // Supongamos que ya tienes los IDs de los partidos que le gustan al jugador
       usersViewModel.likedMatches.observe(viewLifecycleOwner){likedIds->
           matchesViewModel.cargarLikedMatchesList(likedIds)
       }

       matchesViewModel.likedMatchesLiveData.observe(viewLifecycleOwner){likedMatches->
           adapter.submitList(likedMatches)
       }

        return view
    }
}