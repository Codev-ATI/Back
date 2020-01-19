package fr.codev.projectk.rooms

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.User
import fr.codev.projectk.robj.PlayerStatus
import fr.codev.projectk.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

@Service
class RoomsManager {

    @Autowired
    private lateinit var taskRunner: RoomNRTasks

    @Autowired
    private lateinit var gameService: GameService

    private var roomsList: HashMap<String, Room> = hashMapOf();

    fun joinRoom(roomId: String, pseudo: String): List<PlayerStatus>? {
        var room = roomsList[roomId]

        if (room != null && room.isNotHere(pseudo)) {
            return room.join(pseudo)
        }

        return null
    }

    /*
    Return : roomId: String
     */
    fun createGame(id : String): String {
        var builder = StringBuilder()
        var rand = Random()
        var number = 0

        for(i in 1..4) {
            number = rand.nextInt(36)

            number += if (number > 9) {
                55
            } else {
                48
            }

            builder.append(number.toChar())
        }

        var generateId = builder.toString()

        roomsList[generateId] = Room(generateId, gameService.getQuiz(id))

        return generateId
    }

    @Synchronized fun ready(roomId: String, pseudo: String): List<PlayerStatus>? {
        var room = roomsList[roomId]

        var list = room?.setReady(pseudo)

        thread {
            if (room?.allReady()!!) {
                gameThread(roomId, room)
            }
        }

        return list
    }

    fun answer(roomId: String, pseudo: String, questionId: Int, answer: Int) {
        var room = roomsList[roomId]

        room?.answer(pseudo, questionId, answer)
    }

    fun gameThread(roomId: String, room: Room) {
        Thread.sleep(3000)
        taskRunner.sendNextQuestion(roomId, room.getNextQuestion())

        Thread.sleep(5_000)

        taskRunner.sendAnswer(roomId, room.getAnswer())

        if (room.existNextQuestion()) {
            gameThread(roomId, room)
        } else {
            taskRunner.sendStats(roomId, room.giveMeStats())
            roomsList.remove(roomId)
        }
    }
}