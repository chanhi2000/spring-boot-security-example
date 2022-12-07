package com.example.markiiimark.springbootsecurityexample.dto

import java.io.Serializable

data class AuthenticationRequest(
    val email: String,
    val password: String,
): Serializable {
}