package fr.codev.projectk.rooms

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
        roomsList["1"] = Room()
    }

    public fun joinRoom(roomId: String, user: User) {
        var room = roomsList[roomId]

        room!!.join(user);
    }

    public fun leaveRoom(roomId: String, userId: String) {
        var room = roomsList[roomId]

        room!!.leave(userId);
    }

    /*
    Return : roomId: String
     */
    public fun createGame(): String {
        var builder = StringBuilder()
        var rand = Random()
        var number = 0

        for(i in 1..4) {
            number = rand.nextInt(36)

            number += if (number > 10) {
                55
            } else {
                48
            }

            builder.append(number.toChar())
        }

        var generateId = builder.toString()

        roomsList[generateId] = Room()

        return generateId
    }
}