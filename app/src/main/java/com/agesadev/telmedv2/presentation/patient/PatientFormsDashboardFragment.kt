package com.agesadev.telmedv2.presentation.patient

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.agesadev.telmedv2.data.services.BluetoothService
import com.agesadev.telmedv2.databinding.FragmentPatientFormsDashboardBinding
import com.agesadev.telmedv2.presentation.forms.BluetoothDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientFormsDashboardFragment : Fragment() {

    private var _binding: FragmentPatientFormsDashboardBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPatientFormsDashboardBinding.inflate(inflater, container, false)

        bluetoothClickListener()




        
        return binding.root
    }

    private fun bluetoothClickListener() {
        binding.bluetooth.setOnClickListener {
            val dialog = BluetoothDialog()
            dialog.showNow(requireFragmentManager(), "BluetoothDialog")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}