package com.agesadev.telmedv2.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.databinding.FragmentDetailPageBinding
import com.agesadev.telmedv2.presentation.forms.BluetoothDialog

class DetailPage : Fragment() {
    private lateinit var binding: FragmentDetailPageBinding
    private lateinit var patientInfo: PatientInfo

    private val args: DetailPageArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPageBinding.inflate(inflater, container, false)

        binding.patient = args.patient

        binding.bluetoothUpdateIcon.setOnClickListener {
            openBluetoothDialog()
        }

        return binding.root
    }

    private fun openBluetoothDialog() {
        val dialog = BluetoothDialog()
        dialog.showNow(requireFragmentManager(), "BluetoothDialog")
    }


}