package fr.codev.projectk.rooms

import fr.codev.projectk.model.User
import fr.codev.projectk.robj.ContentMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
public class RoomsController() {

    @Autowired
    lateinit var roomManager: RoomsManager

    @MessageMapping("/rooms/join")
    @SendTo("/topic/messages")
    public fun joinRoom(message: ContentMessage): String {

       roomManager.joinRoom(message.roomId, User(message.userId))

        return "HELLO";
    }

    @MessageMapping("/rooms/leave")
    @SendTo("/topic/messages")
    public fun leaveRoom(message: ContentMessage): String {

        roomManager.leaveRoom(message.roomId, message.userId)

        return "BYE";
    }

    @MessageMapping("/rooms/ready")
    @SendTo("/topic/messages")
    public fun readyUser(message: ContentMessage): String {

        // TODO: complete fun content

        return "READY";
    }
}