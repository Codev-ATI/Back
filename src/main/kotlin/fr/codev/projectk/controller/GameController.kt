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
    fun createGame(@PathVariable id: String): ResponseEntity<String> {

        return try {
            var userId: String = roomManager.createGame(id)
            ResponseEntity(userId, HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity("", HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/joinGame/{roomId}/{pseudo}")
    fun joinGame(@PathVariable roomId: String, @PathVariable pseudo: String?): ResponseEntity<PlayerInfos> {

        return try {
            if (pseudo.isNullOrEmpty()) {
                throw NoSuchElementException()
            }
            ResponseEntity(roomManager.joinRoom(roomId, pseudo), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
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