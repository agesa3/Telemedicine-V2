package com.agesadev.telmedv2.presentation.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.databinding.FragmentRegisterPatientBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPatientFragment : Fragment() {

    private var _binding: FragmentRegisterPatientBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PatientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterPatientBinding.inflate(inflater, container, false)
        val view = binding.root


        registrationListener()
        registerClickListener()

        return view
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
            navigateToFormsPage(name, number)
        }

    }

    private fun registerPatient(name: String, number: String) {
        viewModel.patientDetails.fullName = name
        viewModel.patientDetails.phoneNumber = number
    }

    private fun navigateToFormsPage(name: String, phone_number: String) {
        val action = RegisterPatientFragmentDirections
            .actionRegisterPatientFragmentToPatientFormsDashboardFragment(viewModel.patientDetails)
        findNavController().navigate(action)
    }

    private fun registrationListener() {
         viewModel.patientRegistered.observe(viewLifecycleOwner) { state ->
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
                    navigateToFormsPage(
                        binding.patientName.text.toString(),
                        binding.patientNumber.text.toString()
                    )
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