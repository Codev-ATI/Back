package fr.codev.projectk.exceptions

import java.util.*

class ErrorDetails(val timestamp: Date, code: SpecialCode?, details: String?) {
    private val specialCode: Int
    private val details: String?

    init {
        specialCode = code?.value ?: 0
        this.details = details
    }
}