package com.backend.commitoclock.shared.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
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
            it.anyRequest().permitAll()
        }
        .formLogin { it.disable() }
        .httpBasic { it.disable() }
        .build()

    private fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.allowedPath() =
        this.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

    private fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.anyRequestAllowed() =
        this.anyRequest().authenticated()
}
