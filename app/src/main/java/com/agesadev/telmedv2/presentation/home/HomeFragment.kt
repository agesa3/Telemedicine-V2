package com.agesadev.telmedv2.presentation.home

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    private val patientsRecyclerAdapter: PatientsRecyclerAdapter = PatientsRecyclerAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeBinding?.logoutImageIcon?.setOnClickListener {
            authViewModel.logout()
            findNavController().navigate(R.id.loginFragment)
        }
        return homeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
//        homeBinding?.toolbar?.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.logout_icon -> {
//                    Toast.makeText(requireContext(), "Logout", Toast.LENGTH_SHORT).show()
////                    authViewModel.logout()
////                    findNavController().navigate(R.id.loginFragment)
//                }
//            }
//            true
//        }
    }

    private fun setUpRecyclerView() {
        homeBinding?.patientsRecyclerView?.adapter = patientsRecyclerAdapter
    }

}