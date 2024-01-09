package com.uva.padelconnect.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.uva.padelconnect.R
import com.uva.padelconnect.modelView.viewmodel.RankingViewModel

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

        // Observa la LiveData del ViewModel que contiene la lista de RankingsWithUserDetails
        viewModel.top10Ranking.observe(viewLifecycleOwner, Observer { rankingList ->
            cardContainer?.removeAllViews() // Limpia cualquier vista previamente añadida

            if (rankingList.isNullOrEmpty()) {
                // Maneja el caso en el que la lista de RankingsWithUserDetails esté vacía o nula
                // Puedes mostrar un mensaje de "No hay datos disponibles" u otra lógica según tus necesidades
                val emptyDataTextView = TextView(requireContext())
                emptyDataTextView.text = "No hay datos disponibles"
                cardContainer?.addView(emptyDataTextView)
                Log.d("LOG", "NULA O VACIA -------------------------------------")
            } else {
                // La lista no está vacía, así que crea y configura los CardViews
                rankingList.forEach { rankingWithUser ->
                    // Infla una nueva instancia de la plantilla CardView
                    val cardView = layoutInflater.inflate(R.layout.cardviewplayer, cardContainer, false)

                    // Configura y personaliza esta instancia de CardView según los datos
                    val rankTextView = cardView.findViewById<TextView>(R.id.textViewPlayerRank)
                    val changeImageView = cardView.findViewById<ImageView>(R.id.imageViewRankChange)
                    val nameTextView = cardView.findViewById<TextView>(R.id.textViewPlayerName)
                    val locationTextView = cardView.findViewById<TextView>(R.id.textViewPlayerLocation)
                    val pointsTextView = cardView.findViewById<TextView>(R.id.textViewPlayerPoints)
                    val profileImageView = cardView.findViewById<ImageView>(R.id.imageViewPlayerProfile)

                    // Asigna datos a los elementos de la vista dentro de este CardView
                    // rankTextView.text = rankingWithUser.ranking.position.toString()
                    // Configura otros elementos con los datos correspondientes

                    // Agrega esta instancia de CardView personalizada al contenedor
                    cardContainer?.addView(cardView)
                }
            }
        })
    }

}