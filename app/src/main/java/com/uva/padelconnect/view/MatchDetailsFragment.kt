package com.uva.padelconnect.view

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
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

    private val args: MatchDetailsFragmentArgs by navArgs()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val matchId = args.matchId
        var match:Match
        matchesViewModel.getMatchById(matchId).observe(viewLifecycleOwner) { retrievedMatch:Match ->
            match=retrievedMatch
            setDatos(retrievedMatch)
        }
        binding.perfil2.setOnClickListener {
            if (binding.perfil2.drawable.constantState != ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_add
                )?.constantState
            ) {
            } else {
                usersSession.profileImage.observe(viewLifecycleOwner) { imageUrl: Uri ->
                    if (imageUrl!=null) {
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                            .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                            .into(binding.perfil2)
                    }
                }
                usersSession.userId.observe(viewLifecycleOwner) { userId: String ->
                    // Aquí puedes utilizar el valor del userId
                    if (!userId.isNullOrEmpty()) {
                        matchesViewModel.updateMatchUserId(matchId,userId, 2)
                    }
                }
            }
        }
        binding.perfil3.setOnClickListener {
            if (binding.perfil3.drawable.constantState != ContextCompat.getDrawable(this, R.drawable.ic_add)?.constantState) {
            } else {
                usersSession.profileImage.observe(viewLifecycleOwner) { imageUrl: Uri ->
                    if (imageUrl!=null) {
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                            .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                            .into(binding.perfil3)
                    }
                }
                usersSession.userId.observe(viewLifecycleOwner) { userId: String ->
                    // Aquí puedes utilizar el valor del userId
                    if (!userId.isNullOrEmpty()) {
                        matchesViewModel.updateMatchUserId(matchId,userId, 3)
                    }
                }
            }
        }
        binding.perfil4.setOnClickListener {
            if (binding.perfil4.drawable.constantState != ContextCompat.getDrawable(this, R.drawable.ic_add)?.constantState) {
            } else {
                usersSession.profileImage.observe(viewLifecycleOwner) { imageUrl: Uri ->
                    if (imageUrl!=null) {
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                            .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                            .into(binding.perfil4)
                    }
                }
                usersSession.userId.observe(viewLifecycleOwner) { userId: String ->
                    // Aquí puedes utilizar el valor del userId
                    if (!userId.isNullOrEmpty()) {
                        matchesViewModel.updateMatchUserId(matchId,userId, 4)
                    }
                }
            }
        }

        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Aquí obtienes la fecha seleccionada
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)
            // Suponiendo que match es tu objeto Match con la fecha límite
            binding.editTextResultado.isEnabled = selectedDate.after(match.date)
        }, year, month, day)
        datePicker.show()
        binding.editTextResultado.
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
    private fun setDatos(match: Match) {

        // Luego, puedes usar matchesViewModel para obtener los detalles del partido según el ID

        binding.textViewDate.text = match.date.toString()
        if (match.doubles) {
            if (match.public) {
                binding.textViewDatos.text = "Dobles | Publico"
            } else {
                binding.textViewDatos.text = "Dobles | Privado"
            }
        } else {
            if (match.public) {
                binding.textViewDatos.text = "Individual | Publico"
            } else {
                binding.textViewDatos.text = "Individual | Privado"
            }
        }
        var user1:Uri?=null
        var user2:Uri?=null
        var user3:Uri?=null
        var user4:Uri?=null

        matchesViewModel.getPerfilPhoto(match.idUser1,2)
        matchesViewModel.fotoPerfilUri1LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
            user1=fotoPerfilUri
        }

        if(match.idUser2.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser2,2)
            matchesViewModel.fotoPerfilUri2LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user2=fotoPerfilUri
            }
        }
        if(match.idUser3.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser3,3)
            matchesViewModel.fotoPerfilUri3LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user3=fotoPerfilUri
            }
        }
        if(match.idUser4.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser3,4)
            matchesViewModel.fotoPerfilUri4LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user4=fotoPerfilUri
            }
        }

        Glide.with(this)
                .load(user1) // URL de la primera imagen de perfil
                .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                .into(binding.perfil1) // ImageView donde se muestra la imagen
        Glide.with(this)
                .load(user3) // URL de la primera imagen de perfil
                .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                .error(R.drawable.ic_perfil_inf) // Imagen de error si la carga falla
                .into(binding.perfil3) // ImageView donde se muestra la imagen
        Glide.with(this)
                .load(user2)
                .placeholder(if (match.doubles) R.drawable.ic_white else R.drawable.ic_white)
                .error(R.drawable.ic_perfil_inf)
                .into(binding.perfil2)
        Glide.with(this)
                .load(user4)
                .placeholder(if (match.doubles) R.drawable.ic_white else R.drawable.ic_white)
                .error(R.drawable.ic_perfil_inf)
                .into(binding.perfil4)

        val placesClient = Places.createClient(requireContext())
        val fields = listOf(Place.Field.ID, Place.Field.LAT_LNG)
        val findPlaceRequest = FindPlaceRequest.newInstance(address, fields)

        placesClient.findPlace(findPlaceRequest)
            .addOnSuccessListener { response: FindPlaceResponse ->
                if (response != null && response.placeLikelihoods.isNotEmpty()) {
                    val place = response.placeLikelihoods[0].place
                    val latLng = place.latLng
                    if (latLng != null) {
                        val mapView = binding.mapView
                        mapView.getMapAsync { googleMap ->
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                            googleMap.addMarker(MarkerOptions().position(latLng).title("Marker"))
                        }
                    }
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