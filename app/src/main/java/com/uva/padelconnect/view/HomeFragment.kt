package com.uva.padelconnect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.uva.padelconnect.R
import com.uva.padelconnect.modelView.viewmodel.HomeViewModel
import com.uva.padelconnect.databinding.FragmentHomeBinding
import com.uva.padelconnect.databinding.FragmentMatchDetailsBinding
import com.uva.padelconnect.modelView.viewmodel.UsersSessionViewModel

class HomeFragment : Fragment() {
    private lateinit var usersSession: UsersSessionViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        usersSession = ViewModelProvider(this).get(UsersSessionViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(usersSession.userId.value?.isNotEmpty() == true) {
            binding.tvGreeting.text="Hola, "+usersSession.username.toString()+"!"
            binding.tvMatchesWon.text=usersSession.puntos.toString()+" ptos"
            Glide.with(requireContext())
                .load(usersSession.profileImage.value)
                .into(binding.ivUserProfile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}