package com.agesadev.telmedv2.data.models

data class PersonalInfo(
    val fullName: String,
    val gender: String,
    val dateOfBirth: String,
    val idNo: Int,
    val location: String,
    val height: String? = null,
    val weight: String? = null
)