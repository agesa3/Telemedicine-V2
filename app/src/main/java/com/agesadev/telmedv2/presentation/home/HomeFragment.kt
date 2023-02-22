package com.agesadev.telmedv2.presentation.home

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agesadev.telmedv2.R
import com.agesadev.telmedv2.databinding.FragmentHomeBinding
import com.agesadev.telmedv2.presentation.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
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
        getAndObservePatients()
        setUpRecyclerView()

    }

    private fun getAndObservePatients() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.patients.collectLatest { state ->
                    when {
                        state.patients.isNotEmpty() -> {
                            patientsRecyclerAdapter.submitList(state.patients)
                            Log.d("Patients", "getAndObservePatients: ${state.patients}")
                        }
                        state.isLoading -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                        state.isError -> {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        homeBinding?.patientsRecyclerView?.adapter = patientsRecyclerAdapter
        homeBinding?.patientsRecyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

}