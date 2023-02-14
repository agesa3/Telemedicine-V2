package com.agesadev.telmedv2.presentation.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agesadev.telmedv2.R
import com.agesadev.telmedv2.databinding.FragmentHomeBinding
import com.agesadev.telmedv2.presentation.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeBinding?.logout?.setOnClickListener {
            authViewModel.logout()
            findNavController().navigate(R.id.loginFragment)
        }
        return homeBinding?.root
    }


}