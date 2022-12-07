package com.example.markiiimark.springbootsecurityexample.config

import com.example.markiiimark.springbootsecurityexample.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * SecurityConfig
 *
 * <p>기본 Security 설정
 *
 * @author chlee
 */
@EnableWebSecurity
class SecurityConfig {

    @Autowired private lateinit var jwtAuthFilter: JwtAuthFilter
    @Autowired private lateinit var userDao: UserDao

    @Bean
    fun securityFilterChain(http :HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/**/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider().apply {
            this.setUserDetailsService(userDetailsService())
            this.setPasswordEncoder(passwordEncoder())
        }
        return authenticationProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        NoOpPasswordEncoder.getInstance()

    @Bean
    fun userDetailsService(): UserDetailsService =
        object : UserDetailsService {
            override fun loadUserByUsername(username: String?): UserDetails {
                return userDao.findUserByEmail(username ?: "")
                    ?: throw UsernameNotFoundException("No user was found")
            }

        }
}