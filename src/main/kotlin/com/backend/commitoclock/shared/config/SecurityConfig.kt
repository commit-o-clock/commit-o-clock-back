package com.backend.commitoclock.shared.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http.csrf { it.disable() }
        .authorizeHttpRequests {
            it
                .allowedPath()
                .anyRequestAllowed()
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
        .build()

    private fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.allowedPath() =
        this.requestMatchers(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/*"
        ).permitAll()

    private fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.anyRequestAllowed() =
        this.anyRequest().authenticated()
}
