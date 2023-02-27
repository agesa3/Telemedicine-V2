package com.agesadev.telmedv2.presentation.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.telmedv2.R
import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.databinding.FragmentHomeBinding
import com.agesadev.telmedv2.presentation.auth.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.annotation.meta.When

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!
    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private var patientListInfo : List<PatientInfo> = emptyList()

    private val TAG = this::class.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        patientListListener()


        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout_icon -> {
                    authViewModel.logout()
                    findNavController().navigate(R.id.loginFragment)
                }
                R.id.add_patient -> {
                    openPatientRegistration()
                }
            }
            true
        }
    }

    private fun patientListListener() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homeViewModel.patients.collectLatest { state ->
                when {
                    state.isLoading -> {
                        showProgressBar()
                    }
                    state.patients.isNotEmpty() -> {
                        showProgressBar(false)
                        val recyclerAdapter = PatientsRecyclerAdapter()
                        recyclerAdapter.submitList(state.patients)
                        patientListInfo = state.patients
                        homeBinding.patientRecyclerView.adapter = recyclerAdapter
                        homeBinding.patientRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                    state.isError -> {
                        showProgressBar(false)
                        showSnackbar(state.errorMessage?:"Unknown Error Occurred")
                    }
                }
            }
        }
    }

    private fun openPatientRegistration() {
        findNavController().navigate(R.id.registerPatientFragment)
    }

    private fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(homeBinding.root, message, length).show()
    }

    private fun showProgressBar(isVisible: Boolean = true) {
        if(isVisible) {
            homeBinding.patientProgressBar.visibility = View.VISIBLE
        } else {
            homeBinding.patientProgressBar.visibility = View.GONE
        }
    }

//    override fun onClick(p0: View?) {
//        Log.d(TAG, "onClick: Patient view clicked")
//        val itemPosition =
//        val patient = patientListInfo[itemPosition]
//        val action = HomeFragmentDirections.actionHomeFragmentToDetailPage2(patient)
//        findNavController().navigate(action)
//    }
}