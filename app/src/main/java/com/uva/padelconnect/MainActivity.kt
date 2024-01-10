package com.uva.padelconnect

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.app
import com.google.firebase.appcheck.AppCheckProviderFactory
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.auth
import com.uva.padelconnect.databinding.ActivityMainBinding
import com.uva.padelconnect.modelView.viewmodel.SettingsViewModel
import com.uva.padelconnect.view.CreateMatchFragment
import com.uva.padelconnect.view.HomeFragment
import com.uva.padelconnect.view.LoginFragment
import com.uva.padelconnect.view.MatchesFragment
import com.uva.padelconnect.view.MatchesLikedFragment
import com.uva.padelconnect.view.MatchesPlayedFragment
import com.uva.padelconnect.view.ProfileFragment
import com.uva.padelconnect.view.RankingFragment
import com.uva.padelconnect.view.SettingsFragment
import com.uva.padelconnect.view.TournamentFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicialización de Firebase
        FirebaseApp.initializeApp(this)
        Firebase.appCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance())
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
            auth.currentUser!!.email?.let { Log.d("LOG", it) }
            replaceFragment(HomeFragment())
            //usuario existe
        } else {
            replaceFragment(LoginFragment())
        }
        binding.drawerLayout.close()
        binding.imageViewMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profileFragment -> {
                    // Handle item 1 click
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.matchesLikedFragment -> {
                    // Handle item 1 click
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    replaceFragment(MatchesLikedFragment())
                    true
                }
                R.id.matchesPlayedFragment -> {
                    // Handle item 1 click
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    replaceFragment(MatchesPlayedFragment())
                    true
                }
                R.id.settingsFragment -> {
                    // Handle item 1 click
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    replaceFragment(SettingsFragment())
                    true
                }
                // Add more cases as needed
                else -> false
            }
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

        binding.createMatch.setOnClickListener {
            replaceFragment(CreateMatchFragment())
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