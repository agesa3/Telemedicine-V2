package com.agesadev.telmedv2.presentation.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.lifecycle.withStarted
import androidx.navigation.fragment.findNavController
import com.agesadev.telmedv2.R
import com.agesadev.telmedv2.data.models.PersonalInfo
import com.agesadev.telmedv2.databinding.FragmentRegisterPatientBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterPatientFragment : Fragment() {

    private var _binding: FragmentRegisterPatientBinding? = null
    private val binding = _binding!!

    private val viewModel: PatientViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterPatientBinding.inflate(layoutInflater, container, false)


        registrationListener()
        binding.registerPatientButton.setOnClickListener {
            registerClickListener()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun registerClickListener() {
        binding.registerPatientButton.setOnClickListener {
            val name = binding.patientName.text.toString()
            val number = binding.patientNumber.text.toString()

            registerPatient(name, number)
        }

    }

    private fun registerPatient(name: String, number: String) {
        val patient = PersonalInfo(name, number)
        viewModel.registerPatient(patient)
    }

    private fun navigateToFormsPage() {
        findNavController().navigate(R.id.patientFormsDashboardFragment)
    }

    private fun registrationListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.patientRegistered.collectLatest { state ->
                    when {
                        state.isLoading -> {
                            showProgressBar(true)
                        }
                        state.error.isNotEmpty() -> {
                            showProgressBar(false)
                            showSnackBar(state.error)
                        }
                        state.user != null -> {
                            showProgressBar(false)
                            navigateToFormsPage()
                        }
                    }
                }
            }
        }
    }

    private fun showProgressBar(visible: Boolean = false) {
        if(visible) {
            binding.registrationLoader.visibility = View.VISIBLE
        } else {
            binding.registrationLoader.visibility = View.GONE
        }
    }

    private fun showSnackBar(message: String = "", length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(binding.root, message, length).show()
    }

}