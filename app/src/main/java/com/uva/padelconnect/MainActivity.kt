package com.uva.padelconnect

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.uva.padelconnect.databinding.ActivityMainBinding
import com.uva.padelconnect.model.firebase.DatabaseConnection
import com.uva.padelconnect.modelView.viewmodel.SettingsViewModel
import com.uva.padelconnect.view.HomeFragment
import com.uva.padelconnect.view.LoginFragment
import com.uva.padelconnect.view.MatchesFragment
import com.uva.padelconnect.view.ProfileFragment
import com.uva.padelconnect.view.RankingFragment
import com.uva.padelconnect.view.RegisterFragment
import com.uva.padelconnect.view.SettingsFragment
import com.uva.padelconnect.view.TournamentFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicialización de Firebase
        FirebaseApp.initializeApp(this)

        // Inicializar el ViewModel
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        // Observar el estado del modo oscuro y aplicar el tema correspondiente
        settingsViewModel.getIsDarkModeEnabled().observe(this) { isEnabled ->
            if (isEnabled) {
                setTheme(R.style.Theme_PadelConnect_Dark)
            } else {
                setTheme(R.style.Theme_PadelConnect)
            }
            recreate()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auth = Firebase.auth

        //Checkear si el usuario esta registrado/logeado
        if (auth.currentUser != null) {
            Log.d("LOG", "HELLO")
            auth.currentUser!!.email?.let { Log.d("LOG", it) }
            replaceFragment(HomeFragment())
            //usuario existe
        } else {
            Log.println(Log.INFO, "LOG", "User not loged")
            replaceFragment(RegisterFragment())
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.matchFragment -> {
                    //Navegar al Fragmento del partido
                    val fragment = MatchesFragment()
                    replaceFragment(fragment)
                    true
                }
                R.id.profileFragment -> {
                    //Navegar al perfil
                    val fragment = ProfileFragment()
                    replaceFragment(fragment)
                    true
                }

                R.id.ranking->{
                    // Navegar al ranking
                    val fragment = RankingFragment()
                    replaceFragment(fragment)
                    true
                }

                R.id.tournamentFragment->{
                    //Navegar al Fragmento del Torneo
                    val fragment = TournamentFragment()
                    replaceFragment(fragment)
                    true
                }
                R.id.homeFragment->{
                    //Quedarse en Home
                    replaceFragment(HomeFragment())
                    true
                }
                else -> false
            }
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fragment).commit()
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}