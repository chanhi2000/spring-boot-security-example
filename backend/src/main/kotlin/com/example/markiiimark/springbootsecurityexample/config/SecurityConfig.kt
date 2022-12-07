package com.example.markiiimark.springbootsecurityexample.config

import lombok.extern.slf4j.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

/**
 * SecurityConfig
 *
 * <p>기본 Security 설정
 *
 * @author chlee
 */
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http :HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
        return http.build()
    }
}