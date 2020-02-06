package fr.codev.projectk.exceptions

import java.util.*

class ErrorDetails(val timestamp: Date, code: SpecialCode?, details: String?) {
    val specialCode: Int
    val details: String?

    init {
        specialCode = code?.value ?: 0
        this.details = details
    }
}