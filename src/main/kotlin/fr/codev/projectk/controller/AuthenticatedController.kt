package fr.codev.projectk.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/auth")
class AuthenticatedController {

    @GetMapping("/test")
    fun getTest(): String {
        return "Hello"
    }
}