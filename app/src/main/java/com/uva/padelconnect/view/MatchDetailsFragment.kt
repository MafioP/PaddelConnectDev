package com.uva.padelconnect.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.uva.padelconnect.R
import com.uva.padelconnect.databinding.FragmentMatchDetailsBinding
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.modelView.viewmodel.MatchesViewModel
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel
import java.io.IOException
import java.util.Calendar
import java.util.Locale

class MatchDetailsFragment: Fragment() {

    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var usersSession: UsersSessionViewModel

    private var _binding: FragmentMatchDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var matchId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchId = requireArguments().getString("matchId").toString()
        println("matchID: $matchId")
    }
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
        val match: Match = matchesViewModel.getMatchById2(matchId)!!
        // Usa 'match' como lo necesites en tu fragmento
        //setDatos(match)

        binding.share.setOnClickListener {
            val link = match.codigoUnico
            if (link.isNotEmpty()) {
                val clipboard =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Link", link)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(
                    requireContext(),
                    "Enlace copiado al portapapeles",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Introduce un enlace primero",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.perfil2.setOnClickListener {
            if (binding.perfil2.drawable.constantState != ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_add
                )?.constantState
            ) {
            } else {
                if(usersSession.userId.value?.isEmpty() == true){

                }
                if(!match.public){
                    mostrarDialogoCodigoAcceso { codigoIngresado ->
                        if(codigoIngresado != match.codigoUnico){
                            findNavController().navigate(R.id.action_matchDetailsFragment_to_matchListFragment)
                        }
                    }
                }
                usersSession.profileImage.observe(viewLifecycleOwner) { imageUrl: Uri? ->
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
            if (binding.perfil3.drawable.constantState != ContextCompat.getDrawable(requireContext(), R.drawable.ic_add)?.constantState) {
            } else {
                if(usersSession.userId.value?.isEmpty() == true) {}
                if(!match.public){
                    mostrarDialogoCodigoAcceso { codigoIngresado ->
                        if(codigoIngresado != match.codigoUnico){
                            findNavController().navigate(R.id.action_matchDetailsFragment_to_matchListFragment)
                        }
                    }
                }
                usersSession.profileImage.observe(viewLifecycleOwner) { imageUrl: Uri? ->
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
            if (binding.perfil4.drawable.constantState != ContextCompat.getDrawable(requireContext(), R.drawable.ic_add)?.constantState) {
            } else {
                if(usersSession.userId.value?.isEmpty() == true){}
                if(!match.public){
                    mostrarDialogoCodigoAcceso { codigoIngresado ->
                       if(codigoIngresado != match.codigoUnico){
                           findNavController().navigate(R.id.action_matchDetailsFragment_to_matchListFragment)
                       }
                    }
                }
                usersSession.profileImage.observe(viewLifecycleOwner) { imageUrl: Uri? ->
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
        binding.editTextResultado.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Antes de que el texto cambie
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Cuando el texto está cambiando
            }

            override fun afterTextChanged(s: Editable?) {
                // Después de que el texto cambia, aquí puedes obtener el nuevo texto
                val result = s.toString()
                matchesViewModel.updateResult(matchId,result)
                usersSession.userId.observe(viewLifecycleOwner) { userId: String ->
                    // Aquí puedes utilizar el valor del userId
                    if (!userId.isNullOrEmpty()) {
                        matchesViewModel.setPuntos(userId, 100) { success ->
                            if (success) {
                                // Los puntos del usuario se actualizaron correctamente
                            } else {
                                // Hubo un error al actualizar los puntos
                            }
                        }
                    }
                }

            }
        })

        binding.likeButton.setOnClickListener {
            val matchLiked:Boolean = usersSession.likedMatches.value?.contains(matchId) ?: false

            if(!matchLiked) {
                usersSession.likeMatch(matchId)
                Glide.with(this)
                    .load(R.drawable.ic_liked) // URL de la primera imagen de perfil
                    .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                    .error(R.drawable.ic_like) // Imagen de error si la carga falla
                    .into(binding.likeButton) // ImageView donde se muestra la imagen
            }else{
                usersSession.unlikeMatch(matchId)
                Glide.with(this)
                    .load(R.drawable.ic_like) // URL de la primera imagen de perfil
                    .placeholder(R.drawable.ic_white) // Placeholder mientras carga la imagen
                    .error(R.drawable.ic_liked) // Imagen de error si la carga falla
                    .into(binding.likeButton) // ImageView donde se muestra la imagen
            }
        }
    }

    private fun mostrarDialogoCodigoAcceso(onCodigoIngresado: (String) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_codigo_acceso, null)
        builder.setView(dialogView)
            .setPositiveButton("Unirse") { dialog, _ ->
                // Verificar el código ingresado y unirse al partido
                val editTextCodigo = dialogView.findViewById<EditText>(R.id.editTextCodigo)
                val codigoIngresado = editTextCodigo.text.toString()
                onCodigoIngresado(codigoIngresado)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setDatos(match: Match) {

        binding.textViewBarrio.text=match.place

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
        var user1: Uri? = null
        var user2: Uri? = null
        var user3: Uri? = null
        var user4: Uri? = null

        matchesViewModel.getPerfilPhoto(match.idUser1, 2)
        matchesViewModel.fotoPerfilUri1LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
            user1 = fotoPerfilUri
        }

        if (match.idUser2.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser2, 2)
            matchesViewModel.fotoPerfilUri2LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user2 = fotoPerfilUri
            }
        }
        if (match.idUser3.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser3, 3)
            matchesViewModel.fotoPerfilUri3LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user3 = fotoPerfilUri
            }
        }
        if (match.idUser4.isNotEmpty()) {
            matchesViewModel.getPerfilPhoto(match.idUser3, 4)
            matchesViewModel.fotoPerfilUri4LiveData.observe(viewLifecycleOwner) { fotoPerfilUri ->
                user4 = fotoPerfilUri
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

    }
}