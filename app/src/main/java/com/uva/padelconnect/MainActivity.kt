package com.uva.padelconnect

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.FirebaseApp
import com.uva.padelconnect.databinding.ActivityMainBinding
import com.uva.padelconnect.view.HomeFragment
import com.uva.padelconnect.view.MatchesFragment
import com.uva.padelconnect.view.ProfileFragment
import com.uva.padelconnect.view.RankingFragment
import com.uva.padelconnect.view.TournamentFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

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

                R.id.tournamentFragment->{
                    //Navegar al Fragmento del Torneo
                    val fragment= TournamentFragment()
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

        // Inicialización de Firebase
        FirebaseApp.initializeApp(this)
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