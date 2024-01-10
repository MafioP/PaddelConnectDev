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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.uva.padelconnect.R
import com.uva.padelconnect.databinding.FragmentProfileBinding
import com.uva.padelconnect.model.firebase.DatabaseConnection
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel

@Suppress("DEPRECATION")
class ProfileFragment: Fragment() {

    private val viewModel: UsersSessionViewModel by viewModels()
    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view:View = binding.root

        //(activity as AppCompatActivity).setSupportActionBar(binding.header)

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
        inflater.inflate(R.menu.header_navigation_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}