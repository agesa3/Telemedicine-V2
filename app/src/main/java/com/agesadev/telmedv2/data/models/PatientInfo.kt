package com.agesadev.telmedv2.data.models

data class PatientInfo(
    val fullName: String? = null,
    val phoneNumber: String? = null
) {
    // personal information
    val profileImage: String? = null
    val gender: String? = null
    val dateOfBirth: String? = null
    val idNo: Int? = null
    val location: String? = null
    val height: String? = null
    val weight: String? = null
    val comment: String? = null

    // medical information
    val bloodGroup: String? = null
    val bloodPressure: Int? = null
    val pulseRate: Int? = null
    val smokingStatus: String? = null
    val BMIStatus: String? = null
    val disability: String? = null

    // medical history
    val allergies: List<String>? = null
    val familyHistory: List<String>? = null
    val surgicalHistory: List<String>? = null

    fun getAllergiesInStringFormat() : String? {
        return if (allergies == null) {
            null
        } else {
            var returnString = ""
            for (item in allergies) {
                returnString += "$item \n"
            }
            returnString
        }
    }

    fun getFamilyHistoryInStringFormat() : String? {
        return if (familyHistory == null) {
            null
        } else {
            var returnString = ""
            for (item in familyHistory) {
                returnString += "$item \n"
            }
            returnString
        }
    }

    fun getSurgicalHistoryInStringFormat(): String? {
        return if (surgicalHistory == null) {
            null
        } else {
            var returnString = ""
            for (item in surgicalHistory) {
                returnString += "$item \n"
            }
            returnString
        }
    }
}
