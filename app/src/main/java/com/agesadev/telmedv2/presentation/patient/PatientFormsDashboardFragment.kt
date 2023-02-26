package com.agesadev.telmedv2.presentation.patient

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.agesadev.telmedv2.R
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

//        bluetoothClickListener()




        
        return binding.root
    }

//    private fun bluetoothClickListener() {
//        binding.bluetooth.setOnClickListener {
//            val dialog = BluetoothDialog()
//            dialog.showNow(requireFragmentManager(), "BluetoothDialog")
//
//            val pairingRequestFilter = IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST)
//            registerReceiver(pairingReceiver, pairingRequestFilter)
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}