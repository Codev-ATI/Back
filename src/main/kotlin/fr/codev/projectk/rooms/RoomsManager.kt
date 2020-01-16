package fr.codev.projectk.rooms

import fr.codev.projectk.model.Quiz
import fr.codev.projectk.model.User
import org.springframework.stereotype.Service
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashMap

@Service
class RoomsManager {

    private var roomsList: HashMap<String, Room> = hashMapOf();

    // TODO: Remove for a real application
    constructor() {
        roomsList["1"] = Room("1")
    }

    fun joinRoom(roomId: String, user: User): Quiz? {
        var room = roomsList[roomId]

        if (room != null) {
            room.join(user)
            return room.getQuiz()
        }

        return null
    }

    fun leaveRoom(roomId: String, userId: String): Boolean {
        var room = roomsList[roomId]

        if (room != null) {
            room!!.leave(userId)
        }

        return false
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

        roomsList[generateId] = Room(id)

        return generateId
    }

    fun ready(roomId: String, userId: String) {
        var room = roomsList[roomId]

        room?.setReady(userId)
    }
}