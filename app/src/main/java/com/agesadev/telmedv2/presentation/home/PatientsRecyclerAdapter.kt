package com.agesadev.telmedv2.presentation.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.databinding.PatientCardViewBinding

class PatientsRecyclerAdapter :
    ListAdapter<PatientInfo, PatientsRecyclerAdapter.PatientViewHolder>(patientsDiffUtil) {

    inner class PatientViewHolder(val binding: PatientCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: PatientInfo) {
            binding.apply {
                patientDisplayName.text = patient.fullName
                patientDiagnosis.text = patient.comment
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        return PatientViewHolder(
            PatientCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = getItem(position)
        holder.bind(patient)
    }

}

val patientsDiffUtil = object : DiffUtil.ItemCallback<PatientInfo>() {
    override fun areItemsTheSame(oldItem: PatientInfo, newItem: PatientInfo): Boolean {
        return oldItem.idNo == newItem.idNo
    }

    override fun areContentsTheSame(oldItem: PatientInfo, newItem: PatientInfo): Boolean {
        return oldItem == newItem
    }

}