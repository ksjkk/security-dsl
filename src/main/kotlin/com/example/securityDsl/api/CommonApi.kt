package com.example.securityDsl.api

import com.example.securityDsl.core.util.logger
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class CommonApi {
    private val log = logger()

    @GetMapping("/v1/test")
    fun getTest(request: HttpServletRequest, authentication: Authentication?): String {
        log.info("request : {}", request)
        log.info("authentication : {}", authentication)
        check(false)
        return "OK"
    }
}