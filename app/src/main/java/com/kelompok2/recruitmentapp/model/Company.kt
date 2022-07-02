package com.kelompok2.recruitmentapp.model

data class Company(
    val id: String,
    val companyName: String?,
    val email: String,
    val password: String?,
    val phoneNumber: String?,
    val alamat: String?
)
{
    constructor():this("","","","","","")
}

