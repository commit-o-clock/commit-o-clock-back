package com.backend.commitoclock.shared.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper

class RequestWrappingFilter : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val wrappedRequest =
            if (request is HttpServletRequest) ContentCachingRequestWrapper(request)
            else request
        chain.doFilter(wrappedRequest, response)
    }
}