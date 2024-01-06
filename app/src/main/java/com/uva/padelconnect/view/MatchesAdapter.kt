package com.uva.padelconnect.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uva.padelconnect.R
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.modelView.viewmodel.MatchesViewModel

class MatchesAdapter() : RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    private lateinit var matches: List<Match>

    // Crear ViewHolder
    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Asignar vistas en el ViewHolder
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewLocation: TextView = itemView.findViewById(R.id.textViewLocation)
        val buttonArrow2: ImageButton = itemView.findViewById(R.id.buttonArrow2)
        val perfil1:ImageView = itemView.findViewById(R.id.perfil1)
        val perfil2:ImageView = itemView.findViewById(R.id.perfil2)
        val perfil3:ImageView = itemView.findViewById(R.id.perfil3)
        val perfil4:ImageView = itemView.findViewById(R.id.perfil4)

    }

    // Inflar layout de match_item y devolver MatchViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_item, parent, false)
        return MatchViewHolder(view)
    }

    // Asignar datos a las vistas en cada elemento de la lista
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val matchesViewModel:MatchesViewModel = ViewModelProvider(MatchesFragment())[MatchesViewModel::class.java]
        val match = matches[position]

    }

    // Devolver la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return matches.size
    }

    fun submitList(matches:List<Match>){
        this.matches=matches
    }
}