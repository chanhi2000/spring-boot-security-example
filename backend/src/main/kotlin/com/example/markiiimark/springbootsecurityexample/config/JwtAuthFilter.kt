package com.example.markiiimark.springbootsecurityexample.config

import com.example.markiiimark.springbootsecurityexample.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * JwtAthFilter
 * <p>JWT 인증 필터</p>
 *
 * @author chlee
 */
@Component
class JwtAuthFilter: OncePerRequestFilter() {

    @Autowired private lateinit var userDao: UserDao
    @Autowired private lateinit var jwtUtil: JwtUtil

    @Throws(exceptionClasses = [ServletException::class, IOException::class])
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""
        if (authHeader.isEmpty() || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authHeader.substring(7)
        val userEmail = jwtUtil.extractUsername(jwtToken)
        if (userEmail.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userDao.findUserByEmail(userEmail) ?: throw UsernameNotFoundException("No user was found")
            if (jwtUtil.isTokenValid(jwtToken, userDetails)) {
                val authToken: UsernamePasswordAuthenticationToken
                    = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }

}