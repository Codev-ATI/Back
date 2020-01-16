package fr.codev.projectk.controller

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.ShortQuiz
import fr.codev.projectk.rooms.RoomsManager
import fr.codev.projectk.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController {

    @Autowired
    private lateinit var roomManager: RoomsManager

    @Autowired
    private lateinit var gamesService: GameService

    /*
    Return : gameId: String
     */
    @GetMapping("/createGame/{id}")
    public fun createGame(@PathVariable id: String): ResponseEntity<String> {
        return ResponseEntity(roomManager.createGame(id), HttpStatus.OK)
    }

    @GetMapping("/game")
    fun getGames(page: Pageable): ResponseEntity<List<ShortQuiz>> {

        return ResponseEntity(gamesService.getGames(page), HttpStatus.OK)
    }
}