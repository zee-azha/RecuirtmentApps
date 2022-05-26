package com.example.recuirtmentapp

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