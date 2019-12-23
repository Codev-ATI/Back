package fr.codev.projectk.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
public class TestController() {

    @GetMapping("/bonjour")
    public fun bonjour(): String {
        return "Salut";
    }
}