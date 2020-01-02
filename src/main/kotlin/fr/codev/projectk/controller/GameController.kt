package fr.codev.projectk.controller

import fr.codev.projectk.rooms.RoomsManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController {

    @Autowired
    private lateinit var roomManager: RoomsManager

    /*
    Return : gameId: String
     */
    @GetMapping("/createGame")
    public fun createGame(): String {
        return roomManager.createGame()
    }
}