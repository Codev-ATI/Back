package fr.codev.projectk.controller

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.rooms.RoomsManager
import fr.codev.projectk.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthenticatedController {

    @Autowired
    private lateinit var gamesService: GameService

    @PostMapping("/createQuiz")
    fun createQuiz(@RequestBody quiz: Quiz): ResponseEntity<Quiz> {
        return ResponseEntity(gamesService.createQuiz(quiz), HttpStatus.OK)
    }

    @GetMapping("/clear")
    fun clearRooms(page: Pageable): ResponseEntity<String> {
        gamesService.clearRooms()

        return ResponseEntity("Done", HttpStatus.OK)
    }
}