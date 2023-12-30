package com.uva.padelconnect.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.uva.padelconnect.databinding.FragmentEditProfileBinding
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel


class EditProfileFragment: Fragment() {
    private lateinit var viewModel: UsersSessionViewModel
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var newFoto:Uri
    private var getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            newFoto=uri
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UsersSessionViewModel::class.java)

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.name.observe(viewLifecycleOwner) { name ->
            binding.textViewUserRealName.setText(name)
        }
        viewModel.lastName.observe(viewLifecycleOwner) { lastName ->
            binding.textViewUserLastName.setText(lastName)
        }
        viewModel.username.observe(viewLifecycleOwner) { username ->
            binding.textViewUsername.setText(username)
        }
        viewModel.email.observe(viewLifecycleOwner) { email ->
            binding.textViewUserEmail.setText(email)
        }
        viewModel.password.observe(viewLifecycleOwner) { password ->
            binding.textViewUserPassword.setText(password)
        }
        viewModel.profileImage.observe(viewLifecycleOwner) { uri ->
            Glide.with(requireContext())
                .load(uri)
                .into(binding.imageViewUser)
        }

        binding.btnConfirmar.setOnClickListener {
            val newName = binding.textViewUserRealName.text.toString()
            val newLastName = binding.textViewUserLastName.text.toString()
            val newUsername = binding.textViewUsername.text.toString()
            val newPassword = binding.textViewUserPassword.text.toString()
            val newCity = binding.textViewUserCity.text.toString()
            val newCountry = binding.textViewUserCountry.text.toString()
            viewModel.actualizarDatos(newName,newLastName,newUsername,newPassword,newCity,newCountry,newFoto)
        }

        binding.imageViewUser.setOnClickListener{
            getContent.launch("image/*") // Filtra para mostrar solo imágenes
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val selectedImageUri: Uri? = data.data
                // Aquí puedes cargar la imagen en un ImageView o realizar otras acciones
            }
        }
    }
    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 123
    }

}