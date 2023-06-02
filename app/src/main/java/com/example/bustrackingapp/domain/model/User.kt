package com.example.bustrackingapp.domain.model

data class User(
    val name : String,
    val email : String,
    val phone : String,
    val password : String,
    val imgUrl : String?=null
)
