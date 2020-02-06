package fr.codev.projectk.controller

import fr.codev.projectk.model.Credentials
import fr.codev.projectk.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app")
class LoginController(loginService: LoginService) {

    @Autowired
    private val loginService: LoginService = loginService

    @GetMapping("/login")
    fun login(@RequestParam username: String?, @RequestParam password: String?): ResponseEntity<Credentials> {
        return ResponseEntity(loginService.login(username, password), HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestParam pseudo: String, @RequestParam username: String, @RequestParam password: String): ResponseEntity<String> {
        loginService.register(pseudo, username, password)
        return ResponseEntity("You're registered.", HttpStatus.OK)
    }

}