package com.example.securityDsl.core.config

import com.example.securityDsl.core.util.logger
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

class SimpleApiToken(
        private val principal: SimpleUser,
        authorities: Collection<String>
): AbstractAuthenticationToken(authorities.map { SimpleGrantedAuthority(it) }.toSet()) {

    private val log = logger()

    init {
        log.info("simple api token : {}, {}", principal.name, authorities)
    }

    override fun getCredentials(): Any = ""
    override fun getPrincipal(): Any {
        return principal.name
    }
    override fun isAuthenticated() = true

}