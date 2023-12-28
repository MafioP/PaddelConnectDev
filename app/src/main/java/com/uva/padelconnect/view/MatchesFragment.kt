package com.uva.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uva.padelconnect.R
import com.uva.padelconnect.model.entities.Match

class MatchesFragment :Fragment(){
    private var columnCount = 1
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        /**
         * TODO:: Hay que obtener los partidos y pasarselos al MatchesAdapter
         */
        var matches:List<Match>
        // Inicializar el adaptador (puedes pasar datos vacíos inicialmente)
        val adapter = MatchesAdapter(matches)
        // Establecer el adaptador en el RecyclerView
        recyclerView.adapter = adapter

        return view
    }

}