package com.uva.padelconnect.view

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uva.padelconnect.R
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.modelView.viewmodel.MatchesViewModel
import java.util.Date

class MatchesAdapter(private val matchesViewModel: MatchesViewModel) : RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    private var matches: List<Match> = listOf(Match("match1", false, "Pepe", Date(100000000), "","","","","",false, "","235325"))


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
        matchesViewModel.getPerfilPhoto(match.idUser1,2)
        matchesViewModel.fotoPerfilUri1LiveData.observeForever { fotoPerfilUri ->
            Glide.with(holder.itemView.context)
                .load(fotoPerfilUri) // URL de la primera imagen de perfil
                .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                .into(holder.perfil1) // ImageView donde se muestra la imagen
        }

        if(match.idUser2.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser2,2)
            matchesViewModel.fotoPerfilUri2LiveData.observeForever  { fotoPerfilUri ->
                Glide.with(holder.itemView.context)
                    .load(fotoPerfilUri)
                    .placeholder(if (match.doubles) R.drawable.ic_white else R.drawable.ic_white)
                    .error(R.drawable.ic_perfil_inf)
                    .into(holder.perfil2)
            }
        }
        if(match.idUser3.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser3,3)
            matchesViewModel.fotoPerfilUri3LiveData.observeForever  { fotoPerfilUri ->
                Glide.with(holder.itemView.context)
                    .load(fotoPerfilUri) // URL de la primera imagen de perfil
                    .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                    .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                    .into(holder.perfil3) // ImageView donde se muestra la imagen
            }
        }
        if(match.idUser4.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser3,4)
            matchesViewModel.fotoPerfilUri4LiveData.observeForever  { fotoPerfilUri ->
                Glide.with(holder.itemView.context)
                    .load(fotoPerfilUri)
                    .placeholder(if (match.doubles) R.drawable.ic_white else R.drawable.ic_white)
                    .error(R.drawable.ic_perfil_inf)
                    .into(holder.perfil4)
            }
        }

        holder.buttonArrow2.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString("matchId", match.idMatch)
            val context = holder.itemView.context
            val navController = Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
            navController.navigate(R.id.matchFragment, bundle)
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