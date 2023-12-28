package com.uva.padelconnect.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.uva.padelconnect.R
import com.uva.padelconnect.databinding.FragmentProfileBinding
import com.uva.padelconnect.model.firebase.DatabaseConnection
import com.uva.padelconnect.modelView.viewmodel.ProfileViewModel

@Suppress("DEPRECATION")
class ProfileFragment: Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view:View = binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setHasOptionsMenu(true) // Habilitar la visualización del menú en este fragmento


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observar los cambios en los LiveData y actualizar la interfaz de usuario cuando cambien
        viewModel.name.observe(viewLifecycleOwner) { name ->
            binding.textViewUserRealName.text = name
        }
        viewModel.lastName.observe(viewLifecycleOwner) { lastName ->
            binding.textViewUserLastName.text = lastName
        }
        viewModel.username.observe(viewLifecycleOwner) { username ->
            binding.textViewUsername.text = username
        }
        viewModel.email.observe(viewLifecycleOwner) { email ->
            binding.textViewUserEmail.text = email
        }
        viewModel.password.observe(viewLifecycleOwner) { password ->
            binding.textViewUserPassword.text = password
        }
        viewModel.profileImage.observe(viewLifecycleOwner) { uri ->
            Glide.with(requireContext())
                .load(uri)
                .into(binding.imageViewUser)
        }

        binding.bottomNavigationMenu.setOnNavigationItemSelectedListener { menuItem ->
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val fragment= SettingsFragment()
                replaceFragment(fragment)
                true
            }
            R.id.action_matches_favorites ->{
                true
            }
            R.id.action_matches_played->{
                true
            }
            R.id.action_profile->{
                val auth = DatabaseConnection.getAuthInstance()
                val currentUser = auth.currentUser
                // Verificar si el usuario actual está logeado
                val isLoggedIn = currentUser != null
                    if (isLoggedIn) {
                        // Si el usuario está logeado, dirigir a una pantalla
                        // Suponiendo que la pantalla logeada sea ProfileFragment
                        val fragment = ProfileFragment()
                        replaceFragment(fragment)
                    } else {
                        // Si el usuario no está logeado, dirigir a otra pantalla
                        // Suponiendo que la pantalla no logeada sea LoginFragment
                        val fragment = LoginFragment()
                        replaceFragment(fragment)
                    }
                true
            }
            // Agrega más casos según los elementos de tu menú
            else -> super.onOptionsItemSelected(item)
        }
    }

}