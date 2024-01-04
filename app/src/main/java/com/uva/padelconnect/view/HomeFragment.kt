package com.uva.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.uva.padelconnect.R
import com.uva.padelconnect.modelView.viewmodel.HomeViewModel
import com.uva.padelconnect.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        inflater.inflate(R.layout.fragment_home,container,false)
        val root: View = binding.root

        val textView: TextView = binding.tvGreeting
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    /*
        binding.bottomNavigationMenu.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_partidos -> {
                    //Navegar al Fragmento del partido
                    val fragment = MatchesFragment()
                    replaceFragment(fragment)
                    true
                }
                R.id.action_profile -> {
                    //Navegar al perfil
                    val fragment = ProfileFragment()
                    replaceFragment(fragment)
                    true
                }

                R.id.action_ranking->{
                    //Navegar al Fragmento del Ranking
                    val fragment= RankingFragment()
                    replaceFragment(fragment)
                    true
                }

                R.id.action_tournament->{
                    //Navegar al Fragmento del Torneo
                    val fragment= TournamentFragment()
                    replaceFragment(fragment)
                    true
                }
                R.id.action_inicio->{
                    //Quedarse en Home
                    true
                }
                else -> false
            }
        }*/
    }
    private fun replaceFragment(fragment: Fragment) {
        // Función para reemplazar el Fragment en tu contenedor principal
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}