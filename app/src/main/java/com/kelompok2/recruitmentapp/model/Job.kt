package com.kelompok2.recruitmentapp.model


import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Job(
    @get: Exclude
    var id: String? = null,
    var jobTitle: String? = null,
    var companyName: String?= null,
    var jobCategory: String?= null,
    var jobDescription: String?= null,
    var jobResponsibilities: String?= null,
    var skills: String?= null,
    var education:  String?= null,
    var location: String?= null,
    var salary: String?= null,
    var jobType: String?= null
): Parcelable