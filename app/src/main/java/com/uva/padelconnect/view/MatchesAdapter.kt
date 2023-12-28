package com.uva.padelconnect.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.uva.padelconnect.R
import com.uva.padelconnect.model.entities.Match

class MatchesAdapter(private val matches: List<Match>) : RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    // Crear ViewHolder
    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Asignar vistas en el ViewHolder
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewLocation: TextView = itemView.findViewById(R.id.textViewLocation)
        val buttonArrow2: ImageButton = itemView.findViewById(R.id.buttonArrow2)
        val Perfil1:ImageView = itemView.findViewById(R.id.perfil1)
        val Perfil2:ImageView = itemView.findViewById(R.id.perfil2)
        val Perfil3:ImageView = itemView.findViewById(R.id.perfil3)

    }

    // Inflar layout de match_item y devolver MatchViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_item, parent, false)
        return MatchViewHolder(view)
    }

    // Asignar datos a las vistas en cada elemento de la lista
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.textViewDate.text = match.date
        holder.textViewLocation.text = match.location
        holder.buttonArrow2.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_matchesListFragment_to_matchFragment)
        }
    }

    // Devolver la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return matches.size
    }
}