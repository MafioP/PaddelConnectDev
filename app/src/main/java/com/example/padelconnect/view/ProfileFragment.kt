package com.example.padelconnect.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.padelconnect.R
import com.example.padelconnect.model.entities.User
import com.example.padelconnect.modelView.viewmodel.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class ProfileFragment: Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var textViewUserName:TextView
    private lateinit var textViewUserLastName:TextView
    private lateinit var textViewUsername:TextView
    private lateinit var textViewUserEmail:TextView
    private lateinit var textViewUserPassword:TextView
    private lateinit var imageViewUser:ImageView
    private lateinit var bottomNavigationView:BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //Crear las variables para ponerlas el texto
        textViewUserName = view.findViewById(/* id = */ R.id.textViewUserRealName)
        textViewUserLastName = view.findViewById(R.id.textViewUserLastName)
        textViewUsername = view.findViewById(R.id.textViewUsername)
        textViewUserEmail = view.findViewById(R.id.textViewUserEmail)
        textViewUserPassword = view.findViewById(R.id.textViewPassword)
        imageViewUser = view.findViewById(R.id.imageViewUser)
        bottomNavigationView = view.findViewById(R.id.bottomNavigationMenu)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Observar los cambios en los LiveData y actualizar la interfaz de usuario cuando cambien
        viewModel.name.observe(viewLifecycleOwner, { name ->
            textViewUserName.text = name
        })
        viewModel.lastName.observe(viewLifecycleOwner, { lastName ->
            textViewUserLastName.text = lastName
        })
        viewModel.username.observe(viewLifecycleOwner, { username ->
            textViewUsername.text = username
        })
        viewModel.email.observe(viewLifecycleOwner, { email ->
            textViewUserEmail.text = email
        })
        viewModel.password.observe(viewLifecycleOwner, { password ->
            textViewUserPassword.text = password
        })
        viewModel.profileImage.observe(viewLifecycleOwner, { uri ->
            Glide.with(requireContext())
                .load(uri)
                .into(imageViewUser)
        })

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_partidos -> {
                    //Navegar al Fragmento del partido
                    val fragment = MatchesFragment()
                    replaceFragment(fragment)
                    true
                }
                R.id.action_profile -> {
                    //Mantenerse en el Fragmento del perfil
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
                    //Navegar al Fragmento de Inicio
                    val fragment= HomeFragment()
                    replaceFragment(fragment)
                    true
                }
                else -> false
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        // Función para reemplazar el Fragment en tu contenedor principal
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}