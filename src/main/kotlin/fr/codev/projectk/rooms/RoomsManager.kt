package fr.codev.projectk.rooms

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.User
import fr.codev.projectk.robj.PlayerInfos
import fr.codev.projectk.robj.PlayerStatus
import fr.codev.projectk.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.StringBuilder
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.HashMap
import kotlin.concurrent.thread

@Service
class RoomsManager {

    @Autowired
    private lateinit var taskRunner: RoomNRTasks

    @Autowired
    private lateinit var gameService: GameService

    private var roomsList: HashMap<String, Room> = hashMapOf();

    fun  joinRoom(roomId: String, pseudo: String): PlayerInfos {
        var room = roomsList[roomId]

        if (room == null) {
            throw NoSuchElementException()
        }

        taskRunner.sendStatus(roomId, room!!.getStatus());

        return room?.join(pseudo)!!
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

    @Synchronized fun ready(roomId: String, id: Int): List<PlayerStatus>? {
        var room = roomsList[roomId]

        if(room == null || room!!.inGame()) {
            return null
        }

        var list = room?.setReady(id)

        thread {
            if (room?.allReady()!!) {
                gameThread(roomId, room)
            }
        }

        return list
    }

    fun answer(roomId: String, id: Int, questionId: Int, answer: Int) {
        var room = roomsList[roomId]

        if (room == null || !room!!.inGame()) {
            return
        }

        room?.answer(id, questionId, answer)

        if (room?.everyoneAnswered(questionId)) {
            questionEnding(roomId, room)
        }
    }

    fun gameThread(roomId: String, room: Room) {
        Thread.sleep(3000)
        taskRunner.sendNextQuestion(roomId, room.getNextQuestion())
        var qIndex = room.getActualQuestionIndex()

        Thread.sleep(30_000)

        if (qIndex == room.getActualQuestionIndex()) {
            questionEnding(roomId, room)
        }
    }

    private fun questionEnding(roomId: String, room: Room) {
        taskRunner.sendAnswer(roomId, room.getAnswer())

        if (room.existNextQuestion()) {
            gameThread(roomId, room)
        } else {
            taskRunner.sendStats(roomId, room.giveMeStats())
            roomsList.remove(roomId)
        }
    }
}