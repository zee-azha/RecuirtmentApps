package com.kelompok2.recruitmentapp

data class Users(
    val id: String,
    val userNama: String?,
    val email: String,
    val password: String?,
    val phoneNumber: String?,
    val alamat: String?

) {
    constructor():this("","","","","","")
}