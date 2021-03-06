package fr.codev.projectk.rooms

import fr.codev.projectk.robj.PlayerInfos
import fr.codev.projectk.robj.PlayerStatus
import fr.codev.projectk.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException
import kotlin.concurrent.thread

@Service
class RoomsManager {

    @Autowired
    private lateinit var taskRunner: RoomNRTasks

    @Autowired
    private lateinit var gameService: GameService

    private var roomsList: HashMap<String, Room> = hashMapOf()

    fun  joinRoom(roomId: String, pseudo: String): PlayerInfos {
        val room = roomsList[roomId]

        if (room == null || room.inGame()) {
            throw NoSuchElementException()
        }

        taskRunner.sendStatus(roomId, room.getStatus())

        return room.join(pseudo)
    }

    fun  quit(roomId: String, id: String) {
        val room = roomsList[roomId]

        if (room == null) {
            throw NoSuchElementException()
        }

        room.quit(id)

        val status =  room.getStatus()

        if (status.isEmpty()) {
            roomsList.remove(roomId)
        } else {
            taskRunner.sendStatus(roomId, status)
        }
    }

    fun status(roomId: String): List<PlayerStatus>? {
        val room = roomsList[roomId]

        if(room == null || room.inGame()) {
            return null
        }

        return room.getStatus()
    }

    /*
    Return : roomId: String
     */
    fun createGame(id : String): String {
        val builder = StringBuilder()
        val rand = Random()
        var number: Int

        for(i in 1..4) {
            number = rand.nextInt(36)

            number += if (number > 9) {
                55
            } else {
                48
            }

            builder.append(number.toChar())
        }

        val generateId = builder.toString()

        roomsList[generateId] = Room(generateId, gameService.getQuiz(id))

        thread {
            Thread.sleep(600_000)

            val room = roomsList[generateId]

            if (room == null || room.inGame()) {
                roomsList.remove(generateId)
            }
        }

        return generateId
    }

    @Synchronized fun ready(roomId: String, id: String): List<PlayerStatus>? {
        val room = roomsList[roomId]

        if(room == null || room.inGame()) {
            return null
        }

        val list = room.setReady(id)

        thread {
            if (room.allReady()) {
                gameThread(roomId, room)
            }
        }

        return list
    }

    fun answer(roomId: String, userId: String, questionId: Int, answer: Int) {
        val room = roomsList[roomId]

        if (room == null || !room.inGame()) {
            return
        }

        room.answer(userId, questionId, answer)

        if (room.everyoneAnswered(questionId)) {
            questionEnding(roomId, room)
        }
    }

    fun gameThread(roomId: String, room: Room) {
        Thread.sleep(3000)
        taskRunner.sendNextQuestion(roomId, room.getNextQuestion())
        val qIndex = room.getActualQuestionIndex()

        Thread.sleep(30_000)

        if (qIndex == room.getActualQuestionIndex()) {
            questionEnding(roomId, room)
        }
    }

    private fun questionEnding(roomId: String, room: Room) {
        taskRunner.sendAnswer(roomId, room.getAnswer())

        if (room.existNextQuestion()) {
            room.nextQuestion()
            gameThread(roomId, room)
        } else {
            // Just to block main timer execution
            room.nextQuestion()

            Thread.sleep(3_000)
            taskRunner.sendStats(roomId, room.giveMeStats())
            roomsList.remove(roomId)
        }
    }




    /// GLOBAL MANAGEMENT


    fun clearRooms() {
        roomsList.clear()
    }
}