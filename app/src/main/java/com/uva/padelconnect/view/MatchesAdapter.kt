package com.uva.padelconnect.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uva.padelconnect.R
import com.uva.padelconnect.model.entities.Match

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
        val match = matches[position]
        holder.textViewDate.text = match.date.toString()
        holder.textViewLocation.text = match.place
        if (match.profileImageUrls.isNotEmpty()) {
            Glide.with(holder.itemView)
                .load(match.profileImageUrls[0]) // URL de la primera imagen de perfil
                .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                .into(holder.perfil1) // ImageView donde se muestra la imagen
            Glide.with(holder.itemView)
                .load(match.profileImageUrls[2]) // URL de la primera imagen de perfil
                .placeholder(R.drawable.placeholder) // Placeholder mientras carga la imagen
                .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                .into(holder.perfil3) // ImageView donde se muestra la imagen
            if (match.doubles) {
                Glide.with(holder.itemView)
                    .load(match.profileImageUrls[1]) // URL de la primera imagen de perfil
                    .placeholder(R.drawable.placeholder) // Placeholder mientras carga la imagen
                    .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                    .into(holder.perfil2) // ImageView donde se muestra la imagen
                Glide.with(holder.itemView)
                    .load(match.profileImageUrls[3]) // URL de la primera imagen de perfil
                    .placeholder(R.drawable.placeholder) // Placeholder mientras carga la imagen
                    .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                    .into(holder.perfil4) // ImageView donde se muestra la imagen
            }
        }
        holder.buttonArrow2.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_matchesListFragment_to_matchFragment)
        }
    }

    // Devolver la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return matches.size
    }

    fun submitList(matches:List<Match>){
        this.matches=matches
    }
}