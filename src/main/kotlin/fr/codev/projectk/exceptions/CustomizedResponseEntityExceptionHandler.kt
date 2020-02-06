package fr.codev.projectk.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.Instant
import java.util.*

@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(SNException::class)
    fun handleDBException(ex: SNException, request: WebRequest?): ResponseEntity<ErrorDetails> {
        val e = ErrorDetails(Date.from(Instant.now()), ex.specialCode, ex.message)
        return ResponseEntity(e, ex.statusCode)
    }
}