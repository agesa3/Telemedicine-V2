package com.agesadev.telmedv2.presentation.patient

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.agesadev.telmedv2.data.services.BluetoothService
import com.agesadev.telmedv2.databinding.FragmentPatientFormsDashboardBinding
import com.agesadev.telmedv2.presentation.forms.BluetoothDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientFormsDashboardFragment : Fragment() {

    private var _binding: FragmentPatientFormsDashboardBinding? = null
    private val binding get() = _binding!!

    private val args: PatientFormsDashboardFragmentArgs by navArgs()

    private val viewModel: PatientViewModel by viewModels()

    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPatientFormsDashboardBinding.inflate(inflater, container, false)

        binding.dashboardPtName.text = if (viewModel.patientDetails.fullName != null) {
            Log.d(TAG, "onCreateView: Recovered the name from viewmodel")
            viewModel.patientDetails.fullName
        } else {
            Log.d(TAG, "onCreateView: Recovered the name from the arguments")
            args.patient.fullName
        }
        binding.dashboardPtNumber.text = if (viewModel.patientDetails.phoneNumber != null) {
            Log.d(TAG, "onCreateView: Recovered the phone number from viewmodel")
            viewModel.patientDetails.phoneNumber
        } else {
            Log.d(TAG, "onCreateView: Recovered the phone number from the arguments")
            args.patient.phoneNumber
        }

        bluetoothClickListener()
        personalDetailsClickListener()
        
        return binding.root
    }

    private fun bluetoothClickListener() {
        binding.bluetooth.setOnClickListener {
            val dialog = BluetoothDialog()
            dialog.showNow(requireFragmentManager(), "BluetoothDialog")
        }
    }

    private fun personalDetailsClickListener() {
        binding.personalDetailsCard.setOnClickListener {
            val action = PatientFormsDashboardFragmentDirections.actionPatientFormsDashboardFragmentToPersonalInfoFragment(args.patient)
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}