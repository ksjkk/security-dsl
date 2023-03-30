package com.example.securityDsl.core.config

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.validation.ObjectError

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExceptionResponse(
        var pointer: String = "",
        var code: String = HttpStatus.OK.name,
        var status: Int = HttpStatus.OK.value(),

        @field:JsonIgnore
        var trace: String = "",

        var message: String = "",

        var content: Any? = null,

        var validated: List<ObjectError>? = null
)
