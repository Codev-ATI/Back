package fr.codev.projectk.exceptions

import org.springframework.http.HttpStatus

class SNException(error: String?, val statusCode: HttpStatus, val specialCode: SpecialCode) : RuntimeException(error)