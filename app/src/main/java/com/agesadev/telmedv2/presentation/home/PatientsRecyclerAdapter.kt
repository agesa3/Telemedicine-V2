package com.agesadev.telmedv2.presentation.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.telmedv2.data.models.PersonalInfo
import com.agesadev.telmedv2.databinding.SinglePatientCardBinding

class PatientsRecyclerAdapter :
    ListAdapter<PersonalInfo, PatientsRecyclerAdapter.PatientViewHolder>(patientsDiffUtil) {

    inner class PatientViewHolder(val binding: SinglePatientCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: PersonalInfo) {
            binding.apply {
                patientName.text = patient.fullName
                patientDescription.text = patient.idNo.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        return PatientViewHolder(
            SinglePatientCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = getItem(position)
        holder.bind(patient)
    }

}

val patientsDiffUtil = object : DiffUtil.ItemCallback<PersonalInfo>() {
    override fun areItemsTheSame(oldItem: PersonalInfo, newItem: PersonalInfo): Boolean {
        return oldItem.idNo == newItem.idNo
    }

    override fun areContentsTheSame(oldItem: PersonalInfo, newItem: PersonalInfo): Boolean {
        return oldItem == newItem
    }

}