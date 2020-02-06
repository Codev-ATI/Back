package fr.codev.projectk.controller

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.ShortQuiz
import fr.codev.projectk.robj.PlayerInfos
import fr.codev.projectk.rooms.RoomsManager
import fr.codev.projectk.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/app")
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

        try {
            var id: String = roomManager.createGame(id)
            return ResponseEntity(id, HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            return ResponseEntity("", HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/joinGame/{roomId}/{pseudo}")
    public fun createGame(@PathVariable roomId: String, @PathVariable pseudo: String): ResponseEntity<PlayerInfos> {

        try {
            return ResponseEntity(roomManager.joinRoom(roomId, pseudo), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/game")
    fun getGames(page: Pageable): ResponseEntity<List<ShortQuiz>> {

        return ResponseEntity(gamesService.getGames(page), HttpStatus.OK)
    }

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