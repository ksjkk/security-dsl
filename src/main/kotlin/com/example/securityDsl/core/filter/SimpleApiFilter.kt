package com.example.securityDsl.core.filter

import com.example.securityDsl.core.config.SimpleApiToken
import com.example.securityDsl.core.config.SimpleUser
import com.example.securityDsl.core.util.logger
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter

class SimpleApiFilter(
    defaultProcessesUrl: String
): AbstractAuthenticationProcessingFilter(defaultProcessesUrl) {
    private val log = logger()

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val userRole = SimpleUser.findSimpleUser(
            requireNotNull(request.getHeader("role")) { "role is not exist" }
        )
        return SimpleApiToken(userRole, listOf(userRole.name))
    }

    override fun successfulAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain,
            authResult: Authentication
    ) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }
}