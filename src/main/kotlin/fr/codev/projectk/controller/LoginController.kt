package fr.codev.projectk.controller

import fr.codev.projectk.service.LoginService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(loginService: LoginService) {

    private val loginService: LoginService

    @GetMapping("/login")
    fun login(@RequestParam username: String?, @RequestParam password: String?): ResponseEntity<String> {
        return ResponseEntity(loginService.login(username, password), HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestParam username: String?, @RequestParam password: String?): ResponseEntity<String> {
        loginService.register(username, password)
        return ResponseEntity("You're registered.", HttpStatus.OK)
    }

    init {
        this.loginService = loginService
    }
}