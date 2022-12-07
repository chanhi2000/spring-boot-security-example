package com.example.markiiimark.springbootsecurityexample.controller

import com.example.markiiimark.springbootsecurityexample.config.JwtUtil
import com.example.markiiimark.springbootsecurityexample.dao.UserDao
import com.example.markiiimark.springbootsecurityexample.dto.AuthenticationRequest
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * AuthenticationController
 *
 * <p>인증관련 컨트롤러</p>
 *
 * @author chlee
 */
@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController {

    @Autowired private lateinit var authenticationManager: AuthenticationManager
    @Autowired private lateinit var userDao: UserDao
    @Autowired private lateinit var jwtUtil: JwtUtil

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthenticationRequest
    ): ResponseEntity<String> {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        val user: UserDetails = userDao.findUserByEmail(request.email) ?: return ResponseEntity.status(400).body("Some error has occurred")
        return ResponseEntity.ok(jwtUtil.generateToken(user))
    }
}