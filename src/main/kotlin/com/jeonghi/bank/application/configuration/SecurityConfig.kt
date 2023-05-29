package com.jeonghi.bank.application.configuration

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@EnableMethodSecurity // 메서드 시큐리티 활성화
@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    private val allowedUrls = arrayOf("/", "/swagger-ui/**", "/v1/**", "/sign-up", "/sign-in")	// sign-up, sign-in 추가

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf().disable()
        .headers { it.frameOptions().sameOrigin() }
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()	// 허용할 url 목록을 배열로 분리했
                .anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션을 사용하지 않으므로 STATELESS 설정
        .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter::class.java)	// 추가
        .build()!!

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}