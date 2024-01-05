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
        holder.textViewDate.text = match.date.toString()
        holder.textViewLocation.text = match.place
        var user1: Uri?=null
        var user2: Uri?=null
        var user3: Uri?=null
        var user4: Uri?=null
        matchesViewModel.getPerfilPhoto(match.idUser1,2)
        matchesViewModel.fotoPerfilUri1LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
            user1=fotoPerfilUri
        }

        if(match.idUser2.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser2,2)
            matchesViewModel.fotoPerfilUri2LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user2=fotoPerfilUri
            }
        }
        if(match.idUser3.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser3,3)
            matchesViewModel.fotoPerfilUri3LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user3=fotoPerfilUri
            }
        }
        if(match.idUser4.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser3,4)
            matchesViewModel.fotoPerfilUri4LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user4=fotoPerfilUri
            }
        }

        Glide.with(this)
            .load(user1) // URL de la primera imagen de perfil
            .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
            .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
            .into(perfil1) // ImageView donde se muestra la imagen
        Glide.with(this)
            .load(user3) // URL de la primera imagen de perfil
            .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
            .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
            .into(perfil3) // ImageView donde se muestra la imagen
        Glide.with(this)
            .load(user2)
            .placeholder(if (match.doubles) R.drawable.ic_white else R.drawable.ic_white)
            .error(R.drawable.ic_perfil_inf)
            .into(perfil2)
        Glide.with(this)
            .load(user4)
            .placeholder(if (match.doubles) R.drawable.ic_white else R.drawable.ic_white)
            .error(R.drawable.ic_perfil_inf)
            .into(perfil4)

        holder.buttonArrow2.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_matchesListFragment_to_matchFragment(match.idMatch))
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