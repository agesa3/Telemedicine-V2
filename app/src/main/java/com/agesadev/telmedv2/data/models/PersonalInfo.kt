package com.agesadev.telmedv2.data.models

import kotlin.reflect.KClass

data class PersonalInfo(
    val fullName: String,
    val phoneNumber: String
) {
    val gender: String? = null
    val dateOfBirth: String? = null
    val idNo: Int? = null
    val location: String? = null
    val height: String? = null
    val weight: String? = null
    val comment: String? = null
}