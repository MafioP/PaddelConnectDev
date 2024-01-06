package com.uva.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uva.padelconnect.R
import com.uva.padelconnect.modelView.viewmodel.MatchesViewModel
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel

class MatchesFragment :Fragment(){
    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var usersViewModel:UsersSessionViewModel
    private var columnCount = 1
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        matchesViewModel = ViewModelProvider(this).get(MatchesViewModel::class.java)
        usersViewModel = ViewModelProvider(this).get(UsersSessionViewModel::class.java)

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

        if(usersViewModel.city!=null){
            usersViewModel.city.observe(viewLifecycleOwner) { city ->
                matchesViewModel.getMatchesByCity(city)
            }
        }else{
            matchesViewModel.getMatches()
        }

        matchesViewModel.matchesLiveData.observe(viewLifecycleOwner) { matches ->
            adapter.submitList(matches)
        }

        return view
    }

}