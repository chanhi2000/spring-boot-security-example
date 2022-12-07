package com.example.markiiimark.springbootsecurityexample.dao

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

import java.io.Serializable


@Repository
class UserDao(

): Serializable {
    companion object {
        val APPLICATION_USERS = mutableListOf(
            User("bouali.social@gmail.com", "password", setOf(SimpleGrantedAuthority("ROLE_ADMIN"))),
            User("user.mail@gmail.com", "password", setOf(SimpleGrantedAuthority("ROLE_USER"))),
        )
    }

    fun findUserByEmail(email: String): UserDetails? =
        APPLICATION_USERS.find { u -> u.username == email }

}