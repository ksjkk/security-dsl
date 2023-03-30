package com.example.securityDsl.core.config

import com.example.securityDsl.core.config.SimpleUser.*
import com.example.securityDsl.core.config.SimpleUser.Companion.getMoreLevelAuthorities
import com.example.securityDsl.core.filter.SimpleApiFilter
import com.example.securityDsl.core.util.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    class BasicSecurity {
        private val log = logger()

        companion object {
            const val BASIC_API_PATH = "/api/**"
        }

        @Bean
        fun basicFilterChain(http: HttpSecurity): SecurityFilterChain {
            http {
                csrf {
                    disable()
                }
                addFilterAfter<BasicAuthenticationFilter>(SimpleApiFilter(BASIC_API_PATH))
                authorizeRequests {
                    authorize(BASIC_API_PATH, hasAnyAuthority(getMoreLevelAuthorities(minAuthority = USER)))
                }
                authorizeRequests {
                    authorize("/**", permitAll)
                }
                httpBasic {  }
            }
            return http.build()
        }
    }
}