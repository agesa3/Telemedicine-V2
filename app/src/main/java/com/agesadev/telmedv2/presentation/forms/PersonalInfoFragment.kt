package com.agesadev.telmedv2.presentation.forms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.agesadev.telmedv2.R
import com.agesadev.telmedv2.databinding.FragmentPersonalInfoBinding
import com.agesadev.telmedv2.presentation.patient.PatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {

    private lateinit var binding: FragmentPersonalInfoBinding
    private val viewModel: PatientViewModel by activityViewModels()

    private val args: PersonalInfoFragmentArgs by navArgs()

    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)

        viewModel.patientDetails = args.patient

        saveFormClickListener()

        return binding.root
    }

    private fun captureData() {
        viewModel.patientDetails.idNo = binding.inputIdNo.text.toString().toIntOrNull()
        viewModel.patientDetails.dateOfBirth = binding.inputDob.text.toString()
        viewModel.patientDetails.location = binding.inputLocation.text.toString()
        viewModel.patientDetails.height = binding.inputHeight.text.toString()
        viewModel.patientDetails.weight = binding.inputWeight.text.toString()

        Log.d(TAG, "captureData: Patient Info = ${viewModel.patientDetails}")
    }

    private fun saveFormClickListener(){
        binding.saveFormButton.setOnClickListener {
            captureData()
            navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        val action = PersonalInfoFragmentDirections.actionPersonalInfoFragmentToPatientFormsDashboardFragment(viewModel.patientDetails)
        findNavController().navigate(action)
    }


}