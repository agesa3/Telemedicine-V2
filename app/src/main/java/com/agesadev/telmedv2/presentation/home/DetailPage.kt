package com.agesadev.telmedv2.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.databinding.FragmentDetailPageBinding

class DetailPage : Fragment() {
    private lateinit var binding: FragmentDetailPageBinding
    private lateinit var patientInfo: PatientInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPageBinding.inflate(inflater, container, false)



        return binding.root
    }


}