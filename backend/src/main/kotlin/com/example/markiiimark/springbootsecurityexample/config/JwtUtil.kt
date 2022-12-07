package com.example.markiiimark.springbootsecurityexample.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

import java.util.*
import java.util.function.Function
import java.util.concurrent.TimeUnit

/**
 * JwtUtil
 *
 * <p>JWT인증 관련 유틸</p>
 * @author chlee
 */
@Component
class JwtUtil {
    private val jwtSiginingKey = "secret"
    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun extractExpiration(token: String): Date =
        extractClaim(token, Claims::getExpiration)


    fun hasClaim(token: String, claimName: String): Boolean {
        val claims: Claims = extractAllClaims(token)
        return claims[claimName] != null
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun extractAllClaims(token: String): Claims =
        Jwts.parser()
            .setSigningKey(jwtSiginingKey)
            .parseClaimsJws(token)
            .body


    fun isTokenExpired(token: String) =
        extractExpiration(token).before(Date())

    fun generateToken(userDetails: UserDetails, claims: Map<String, Any> = mutableMapOf()): String {
        return createToken(claims, userDetails)
    }

    private fun createToken(claims: Map<String, Any>, userDetails: UserDetails): String =
        Jwts.builder().setClaims(claims)
            .setSubject(userDetails.username)
            .claim("authorities", userDetails.authorities)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
            .signWith(SignatureAlgorithm.HS256, jwtSiginingKey)
            .compact()

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username: String = extractUsername(token)
        return (username == userDetails.username) &&
                !isTokenExpired(token)
    }

}