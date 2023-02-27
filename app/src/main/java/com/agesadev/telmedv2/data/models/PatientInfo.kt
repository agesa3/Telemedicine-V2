package com.agesadev.telmedv2.data.models

import android.os.Parcel
import android.os.Parcelable

data class PatientInfo(
    var fullName: String? = null,
    var phoneNumber: String? = null
) : Parcelable {
    // personal information
    val profileImage: String? = null
    val gender: String? = null
    var dateOfBirth: String? = null
    var idNo: Int? = null
    var location: String? = null
    var height: String? = null
    var weight: String? = null
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

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        dateOfBirth = parcel.readString()
        idNo = parcel.readValue(Int::class.java.classLoader) as? Int
        location = parcel.readString()
        height = parcel.readString()
        weight = parcel.readString()
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullName)
        parcel.writeString(phoneNumber)
        parcel.writeString(dateOfBirth)
        parcel.writeValue(idNo)
        parcel.writeString(location)
        parcel.writeString(height)
        parcel.writeString(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PatientInfo> {
        override fun createFromParcel(parcel: Parcel): PatientInfo {
            return PatientInfo(parcel)
        }

        override fun newArray(size: Int): Array<PatientInfo?> {
            return arrayOfNulls(size)
        }
    }
}
