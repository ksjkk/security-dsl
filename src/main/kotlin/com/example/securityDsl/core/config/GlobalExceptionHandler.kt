package com.example.securityDsl.core.config

import com.example.securityDsl.core.util.logger
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.AuthorizationServiceException
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.nio.charset.StandardCharsets

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = logger()

    @ExceptionHandler(value = [ AuthenticationServiceException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthException(req: HttpServletRequest, e: Exception): ResponseEntity<ExceptionResponse> {
        val status = HttpStatus.UNAUTHORIZED
        log.warn("::: Exception Handler :::\n| status : {}\n| req header : {}", status, req.headerNames.asSequence().map { "[$it] : ${req.getHeader(it)}" })

        return ResponseEntity(
                createExceptionResponse(req, e, status),
                createHttpHeaders(),
                status
        )
    }

    @ExceptionHandler(value = [ AccessDeniedException::class, AuthorizationServiceException::class ])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiddenException(req: HttpServletRequest, e: Exception): ResponseEntity<ExceptionResponse> {
        val status = HttpStatus.FORBIDDEN
        log.warn("::: Exception Handler :::\n| status : {}\n| req header : {}", status, req.headerNames.asSequence().map { "[$it] : ${req.getHeader(it)}" })

        return ResponseEntity(
                createExceptionResponse(req, e, status),
                createHttpHeaders(),
                status
        )
    }

    @ExceptionHandler(value = [ IllegalArgumentException::class, IllegalStateException::class ])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentStateException(req: HttpServletRequest, e: Exception): ResponseEntity<ExceptionResponse> {
        val status = HttpStatus.BAD_REQUEST
        log.warn("::: Exception Handler :::\n| status : {}\n| req header : {}", status, req.headerNames.asSequence().map { "[$it] : ${req.getHeader(it)}" })

        return ResponseEntity(
                createExceptionResponse(req, e, status),
                createHttpHeaders(),
                status
        )
    }

    private fun createHttpHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
        return headers
    }

    private fun createExceptionResponse(req: HttpServletRequest, e: Exception, status: HttpStatus): ExceptionResponse {
        return ExceptionResponse(
                code = status.name,
                status = status.value(),
                pointer = req.requestURI,
                trace = e.stackTraceToString(),
                message = e.message ?: e.localizedMessage
        )
    }
}