package com.uva.padelconnect.view

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.auth.FirebaseAuth
import com.uva.padelconnect.R
import com.uva.padelconnect.databinding.FragmentMatchDetailsBinding
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.model.entities.User
import com.uva.padelconnect.modelView.viewmodel.MatchesViewModel
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel
import java.util.Calendar

class MatchDetailsFragment: Fragment() {

    //private val args: MatchDetailsFragmentArgs by navArgs()
    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var usersSession: UsersSessionViewModel

    private var _binding: FragmentMatchDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        matchesViewModel = ViewModelProvider(this).get(MatchesViewModel::class.java)
        usersSession = ViewModelProvider(this).get(UsersSessionViewModel::class.java)

        _binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)

        val view=binding.root
        return view
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }


}