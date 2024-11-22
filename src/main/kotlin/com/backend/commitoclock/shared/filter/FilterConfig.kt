package com.backend.commitoclock.shared.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {
    @Bean
    fun requestWrappingFilter(): FilterRegistrationBean<RequestWrappingFilter> {
        val filterRegistrationBean = FilterRegistrationBean(RequestWrappingFilter())
        filterRegistrationBean.order = 1
        return filterRegistrationBean
    }
}