package com.uva.padelconnect.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.uva.padelconnect.R
import com.uva.padelconnect.modelView.viewmodel.RankingViewModel
import com.bumptech.glide.Glide
import android.util.TypedValue


class RankingFragment: Fragment() {
    private val viewModel: RankingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtén una referencia al contenedor de CardViews
        val cardContainer = view.findViewById<LinearLayout>(R.id.container_for_cardviews)

        // Observa la LiveData del ViewModel que contiene la lista de usuarios
        viewModel.top10Ranking.observe(viewLifecycleOwner, Observer { userList ->
            cardContainer?.removeAllViews() // Limpia cualquier vista previamente añadida

            if (userList.isNullOrEmpty()) {
                // Maneja el caso en el que la lista de usuarios esté vacía o nula
                // Puedes mostrar un mensaje de "No hay datos disponibles" u otra lógica según tus necesidades
                val emptyDataTextView = TextView(requireContext())
                emptyDataTextView.text = "No hay datos disponibles"
                cardContainer?.addView(emptyDataTextView)
                Log.d("LOG", "NULA O VACIA -------------------------------------")
            } else {
                // La lista no está vacía, así que crea y configura los CardViews
                userList.forEachIndexed { index, user ->
                    // Infla una nueva instancia de la plantilla CardView
                    val cardView = layoutInflater.inflate(R.layout.cardviewplayer, cardContainer, false)

                    // Configura y personaliza esta instancia de CardView según los datos del usuario
                    val rankTextView = cardView.findViewById<TextView>(R.id.textViewPlayerRank)
                    val nameTextView = cardView.findViewById<TextView>(R.id.textViewPlayerName)
                    val locationTextView = cardView.findViewById<TextView>(R.id.textViewPlayerLocation)
                    val pointsTextView = cardView.findViewById<TextView>(R.id.textViewPlayerPoints)
                    val profileImageView = cardView.findViewById<ImageView>(R.id.imageViewPlayerProfile)

                    // Configura los datos del usuario en los elementos de la vista dentro de este CardView
                    rankTextView.text = (index + 1).toString()
                    nameTextView.text = "${user.name} ${user.lastName}"
                    locationTextView.text = "${user.city}, ${user.country}"
                    pointsTextView.text = user.puntos.toString()

                    if (index == 9) {
                        rankTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21f)
                    }

                    // Cargar la imagen de perfil utilizando Glide
                    Glide.with(requireContext())
                        .load(user.imageView) // La Uri de la imagen de perfil
                        .placeholder(R.drawable.ic_foto_perfil) // Imagen de perfil predeterminada
                        .error(R.drawable.ic_foto_perfil)
                        .into(profileImageView)

                    // Agrega esta instancia de CardView personalizada al contenedor
                    cardContainer?.addView(cardView)
                }
            }
        })

        // Llama al método para obtener la lista de los 10 mejores usuarios por puntuación
        viewModel.getTop10Ranking()
    }

}